#include <cmath>
#include <iostream>
#include <vector>

int getTreeIndex(std::vector<int>* tree, int index) {
        // Convert input data index tree index.
        int dataSize = tree->size() / 2;
        index += dataSize;
        // Check if converted index is valid.
        if (index < dataSize || index >= tree->size()) {
                throw std::invalid_argument("Index out of bounds.");
        }
        return index;
}

int getParentIndex(int index) {
        // Get index of parent.
        return std::floor(index / 2);
}

int getLeftChildIndex(int index) {
        // Get index of left child.
        return 2 * index;
}

int getRightChildIndex(int index) {
        // Get index of right child.
        return getLeftChildIndex(index) + 1;
}

int process(int a, int b) {
        // Specify how two neighboring entries should be processed.
        return a + b;
        // return std::min(a, b);
        // return std::max(a, b);
}

int getRange(std::vector<int>* tree,
                        int leftIndex,
                        int rightIndex) {
        // Convert provided indices to
        // its corresponding tree indices.
        leftIndex = getTreeIndex(tree, leftIndex);
        rightIndex = getTreeIndex(tree, rightIndex);
        // Check if range is valid.
        if (rightIndex < leftIndex) {
                throw std::invalid_argument("Invalid range.");
        }
        // Initialize result variable.
        int result;
        // Create a boolean flag indicating if the
        // result variable is used for the first time.
        bool isFirstResult = true;
        // Move through segment tree from bottom to top.
        while (leftIndex <= rightIndex) {
                // Odd indices indicate a right child in the tree.
                // If the left border is also left child, there is
                // nothing to do because this part of the array is
                // already processed in the parent node.
                if ((leftIndex % 2) == 1) {
                        // Otherwise, the left border is a right child,
                        // therefore add it to the result and move to
                        // its right neighbour in the tree.
                        if (isFirstResult) {
                                result = (*tree)[leftIndex];
                                isFirstResult = false;
                        } else {
                                result = process(result, (*tree)[leftIndex]);
                        }
                        leftIndex++;
                }
                // Even indices indicate a left child in the
                // tree. If the right border is a right child,
                // there is also nothing to do.
                if ((rightIndex % 2) == 0) {
                        // Otherwise, add the right border to the
                        // result and move to its left neighbour.
                        if (isFirstResult) {
                                result = (*tree)[rightIndex];
                                isFirstResult = false;
                        } else {
                                result = process(result, (*tree)[rightIndex]);
                        }
                        rightIndex--;
                }
                // Continue processing parent nodes.
                leftIndex = getParentIndex(leftIndex);
                rightIndex = getParentIndex(rightIndex);
        }
        return result;
}

void updateTree(std::vector<int>* tree, int index, int difference) {
        // Convert provided index to
        // its corresponding tree index.
        index = getTreeIndex(tree, index);
        // Increase or decrease specific leaf
        // node by given difference amount.
        (*tree)[index] += difference;
        // Propagate change through
        // tree from bottom to top.
        index = getParentIndex(index);
        while(index >= 1) {
                (*tree)[index] = process((*tree)[getLeftChildIndex(index)], (*tree)[getRightChildIndex(index)]);
                index = getParentIndex(index);
        }
}

std::vector<int>* createTree(std::vector<int> data) {
        // Resize input data to the next power of 2.
        int dataSize = std::pow(2, std::ceil(std::log(data.size())/std::log(2)));
        data.resize(dataSize);
        // Create array of twice the input
        // size initialized with zeros.
        int treeSize = 2 * dataSize;
        std::vector<int>* tree = new std::vector<int>(treeSize, 0);
        // Copy values of input array to leafs,
        // i.e. to the right half of the tree array.
        for (int i = 0; i < dataSize; i++) {
                (*tree)[dataSize + i] = data[i];
        }
        // Compute values of inner nodes, i.e.
        // the left half of the tree array. Note
        // that the entry at index 0 stays unused,
        // so the array starts with index 1. This
        // procedure simplifies index computation.
        for (int i = dataSize - 1; i > 0; i--) {
                int leftChildIndex = getLeftChildIndex(i);
                int rightChildIndex = getRightChildIndex(i);
                (*tree)[i] = process((*tree)[leftChildIndex], (*tree)[rightChildIndex]);
        }
        // Return tree.
        return tree;
}

int main() {
        // Input data array.
        std::vector<int> data {5, 8, 6, 3, 2, 7, 2, 6};

        // Create segment tree represented as an array.
        std::vector<int>* tree = createTree(data);
        // Process tree.
        std::cout << getRange(tree, 2, 7) << std::endl;
        // Change a single entry.
        updateTree(tree, 4, -1);
        // Process tree again.
        std::cout << getRange(tree, 2, 7) << std::endl;

        // Delete tree.
        delete tree;

        return 0;
}
