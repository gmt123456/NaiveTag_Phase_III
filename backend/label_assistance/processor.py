
import build_model

models = {}


def process_new_model(task_id, pic_path_file, target_labels_file_path): # build a new model
    if task_id in models.keys():
        return
    model = build_model.build_model(pic_path_file, target_labels_file_path)
    if model is not None:
        models[task_id] = model
    print 'Models:', models

def process_update(task_id, tag_file_path):
    model = models.get(task_id, None)
    if model is None:
        return
    path_to_tag = {}
    with open(tag_file_path, 'r') as f:
        lines = f.readlines()
        for line in lines:
            parts = line.strip().split(" ")
            path, tag = parts[0], parts[1]
            path_to_tag[path] = tag

    model.update_tags(path_to_tag)

    print models


def process_predict(task_id, pic_path):
    model = models.get(task_id, None)
    if model is None:
        return []
    potential_tags = model.predict(pic_path) # also a list

    print potential_tags

    return potential_tags