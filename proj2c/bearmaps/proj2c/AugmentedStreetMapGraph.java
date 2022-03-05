package bearmaps.proj2c;

import bearmaps.hw4.streetmap.Node;
import bearmaps.hw4.streetmap.StreetMapGraph;
import bearmaps.lab9.MyTrieSet;
import bearmaps.lab9.TrieSet61B;
import bearmaps.proj2ab.KDTree;
import bearmaps.proj2ab.Point;
import bearmaps.proj2ab.PointSet;

import java.util.*;

/**
 * An augmented graph that is more powerful that a standard StreetMapGraph.
 * Specifically, it supports the following additional operations:
 *
 *
 * @author Alan Yao, Josh Hug, ________
 */
public class AugmentedStreetMapGraph extends StreetMapGraph {
    private final Map<Point, Node> pointToNode;
    private final Map<String, Set<String>> nameConvert;
    private final Map<String, Set<Node>> nameToNode;
    private final PointSet kdt;
    private final TrieSet61B trie;

    public AugmentedStreetMapGraph(String dbPath) {
        super(dbPath);
        pointToNode = new HashMap<>();
        nameConvert = new HashMap<>();
        nameToNode = new HashMap<>();
        trie = new MyTrieSet();

        for (Node node : this.getNodes()) {
            if (!neighbors(node.id()).isEmpty()) {
                pointToNode.put(new Point(node.lon(), node.lat()), node);
            }
            String name = node.name();
            if (name != null && !name.isEmpty()) {
                String cleanName = cleanString(name);
                if (cleanName.isEmpty()) {
                    continue;
                }

                if (!nameConvert.containsKey(cleanName)) {
                    nameConvert.put(cleanName, new HashSet<>());
                }
                nameConvert.get(cleanName).add(name);

                if (!nameToNode.containsKey(cleanName)) {
                    nameToNode.put(cleanName, new HashSet<>());
                }
                nameToNode.get(cleanName).add(node);

                trie.add(cleanName);
            }
        }
        kdt = new KDTree(pointToNode.keySet());
    }


    /**
     * For Project Part II
     * Returns the vertex closest to the given longitude and latitude.
     * @param lon The target longitude.
     * @param lat The target latitude.
     * @return The id of the node in the graph closest to the target.
     */
    public long closest(double lon, double lat) {
        return pointToNode.get(kdt.nearest(lon, lat)).id();
    }


    /**
     * For Project Part III (gold points)
     * In linear time, collect all the names of OSM locations that prefix-match the query string.
     * @param prefix Prefix string to be searched for. Could be any case, with or without
     *               punctuation.
     * @return A <code>List</code> of the full names of locations whose cleaned name matches the
     * cleaned <code>prefix</code>.
     */
    public List<String> getLocationsByPrefix(String prefix) {
        List<String> fullNames = new LinkedList<>();
        for (String cleanName : trie.keysWithPrefix(prefix)) {
            fullNames.addAll(nameConvert.get(cleanName));
        }
        return fullNames;
    }

    /**
     * For Project Part III (gold points)
     * Collect all locations that match a cleaned <code>locationName</code>, and return
     * information about each node that matches.
     * @param locationName A full name of a location searched for.
     * @return A list of locations whose cleaned name matches the
     * cleaned <code>locationName</code>, and each location is a map of parameters for the Json
     * response as specified: <br>
     * "lat" -> Number, The latitude of the node. <br>
     * "lon" -> Number, The longitude of the node. <br>
     * "name" -> String, The actual name of the node. <br>
     * "id" -> Number, The id of the node. <br>
     */
    public List<Map<String, Object>> getLocations(String locationName) {
        String cleanName = cleanString(locationName);
        List<Map<String, Object>> mapList = new LinkedList<>();
        for (Node location : nameToNode.get(cleanName)) {
            Map<String, Object> locInfo = new HashMap<>();
            locInfo.put("lat", location.lat());
            locInfo.put("lon", location.lon());
            locInfo.put("name", location.name());
            locInfo.put("id", location.id());
            mapList.add(locInfo);
        }
        return mapList;
    }


    /**
     * Useful for Part III. Do not modify.
     * Helper to process strings into their "cleaned" form, ignoring punctuation and capitalization.
     * @param s Input string.
     * @return Cleaned string.
     */
    private static String cleanString(String s) {
        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }

}
