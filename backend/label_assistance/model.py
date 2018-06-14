
class Model(object):

    def __init__(self, kd_tree, pic_paths,target_labels):
        self.path_to_tag = {x: None for x in pic_paths}
        self.index_to_path = {i:x for (i, x) in enumerate(pic_paths)}
        self.path_to_index = {x:i for (i, x) in enumerate(pic_paths)}
        self.kd_tree = kd_tree
        self.target_labels = target_labels
        self.recommendation_count = 1 if target_labels < 5 else 3
        self.k = 10
        print self.path_to_tag

    def predict(self, pic_path):
        index = self.path_to_index[pic_path]
        input_image = self.kd_tree.get_arrays()[0][index]
        print input_image
        _, neighbours = self.kd_tree.query([input_image], k=self.k + 1) # k + 1 prohibit itself

        print _[0]

        labels = [self.path_to_tag[self.index_to_path[index]] for index in neighbours[0]]
        labels = [label for label in labels if label is not None]

        dct = dict()
        for i in labels:
            dct[i] = dct.get(i, 0) + 1
        dic = sorted(dct.items(), key=lambda x: x[1], reverse=True)

        results = []
        for i in range(min(self.recommendation_count, len(dic))): # KNN
            results.append(dic[i][0])

        return results


    def update_tags(self, new_labels):
        for path in new_labels:
            self.path_to_tag[path] = new_labels[path]
        print self.path_to_tag