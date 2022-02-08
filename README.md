# Evaluation of the best path

The purpose of this program is to, from a text file, build a graph. This graph is composed of paths, which are themselves composed of vertices linked together with a distance (longitude and latitude), the edges can be "stop" and therefore "slow" the path. The goal is to find the best possible path of this graph (representing itself the journey).

## Example

![Untitled](Evaluation%20of%20the%20best%20path%2016638942acef4090a8dec3c4c6c8b995/Untitled.png)

Here is an example, the starting point is 0, and the last one is 11. The program reads the text file (respecting the syntaxes) which gives the information of the graph: source point, destination point, list of vertices with longitude and latitude, edges, and stops. The program evaluates all possible paths. He then compares for each path of the graph the weight of stops (= nb of stops * 30), and the total Euclidean distance. The best path depends on the sum of these two calculations, here the best is 0 -> 1 -> 3 -> 10 -> 11 which has a rating of 230.

## Output

![Untitled](Evaluation%20of%20the%20best%20path%2016638942acef4090a8dec3c4c6c8b995/Untitled%201.png)