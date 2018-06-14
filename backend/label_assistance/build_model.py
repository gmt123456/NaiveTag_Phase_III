import numpy as np

from sklearn.decomposition import PCA
from sklearn.neighbors import KDTree
from PIL import Image

from model import Model

__all__ = ['build_model']

def build_model(pic_path_file, target_labels_file_path):
    pic_paths = load_pic_paths(pic_path_file) # a pic list --> low to query

    kd_tree = build_KDTree(pic_paths)

    print 'KD_Tree is None? ', kd_tree == None

    if kd_tree is None:
        return None


    labels = load_labels(target_labels_file_path)
    model = Model(kd_tree, pic_paths,labels)

    return model

def load_labels(target_labels_file_path):
    labels = []

    with open(target_labels_file_path, 'r') as f:
        lines = f.readlines()
        for line in lines:
            line = line.strip()
            if len(line) > 0:
                labels.append(line)

    return labels

def load_pic_paths(pic_path_file):
    pic_paths = []

    with open(pic_path_file, 'r') as f:
        lines = f.readlines()
        for line in lines:
            line = line.strip()
            if len(line) > 0:
                pic_paths.append(line)

    return pic_paths

def build_KDTree(pic_paths):

    pic_size = [-1, -1]

    n_components = 20

    gray_images = []
    for pic_path in pic_paths:
        image = Image.open(pic_path).convert('L')  # generate gray image
        if pic_size[0] == -1:
            pic_size[0] = image.size[0]
            pic_size[1] = image.size[1]
            n_components = max(pic_size[0] * pic_size[1] * 0.01, n_components)
        else:
            if pic_size[0] != image.size[0] and pic_size[1] != image.size[1]:
                return None
        gray_images.append(np.array(image).reshape(pic_size[0] * pic_size[1]))

    gray_images = np.array(gray_images)  # n * pic_size, pic_size equals width * height

    print gray_images[0]
    # whiten raw data
    pca = PCA(n_components=n_components, copy=False, whiten=True)

    pca.fit(gray_images)

    gray_images = pca.transform(gray_images)

    print gray_images[0]

    # build KD-Tree
    kd_tree = KDTree(gray_images)  # we can use kd_tree

    return kd_tree