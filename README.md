
The PageRank Algorithm


The algorithm computes the PageRank (PR) of a node A in a directed graph using a recursive definition:

PR(A) = (1-d) + d (PR(T1)/C(T1) + ... + PR(Tn)/C(Tn))

Here d is a damping factor that we will set to 0.85. Nodes T1, T2, ..., Tn are the nodes that connect to A (i.e. having a link going from Ti to A). The term C(Ti) is the number of links outgoing from Ti. The idea is that a node receives its PR from the node connected to it and it distributes its PR to the node it connects to.

Since the computation of the PR of a node requires the knowledge of the PR of its neighboring nodes, the full computation of all PRs has to be done in an iterative way. You first set the PR of all nodes to 1.0. You then sequentially update the PR of each node using the current PR value of its neighbors.Repeat this process several times before the PRs stabilize to their true value. The higher the PR of a node is, the more influence (or importance) this node has in the network.

We have used edge.dnc dataset to implement the following algorithm and found 10 most influential node.
