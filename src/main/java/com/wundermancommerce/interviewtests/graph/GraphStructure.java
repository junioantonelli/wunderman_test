package com.wundermancommerce.interviewtests.graph;

import com.google.common.graph.MutableNetwork;
import com.google.common.graph.NetworkBuilder;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;


public class GraphStructure {

    MutableNetwork<People, Relationship> graph = NetworkBuilder.
            undirected().expectedEdgeCount(100).expectedNodeCount(100).build();

    Map<String, People> peopleMap = new HashMap<String, People>();
    List<Relationship> relationshipList = new ArrayList<Relationship>();

    // 1st of 3 methods created to solve Exercise 1
    public String[] generateAbsolutePaths(String fileName) {
        String[] fileNames = fileName.split(",");
        String[] paths = new String[fileNames.length];
        for (int i = 0; i < fileNames.length; i++) {
            paths[i] = getClass().getClassLoader().getResource(fileNames[i]).getPath();
        }
        return paths;
    }

    // 2nd of 3 methods created to solve Exercise 1
    public void loadFiles(String[] absolutePathFileNames) {
        List<File> fileList = new ArrayList<File>();
        for (int i = 0; i < absolutePathFileNames.length; i++) {
            fileList.add(new File(absolutePathFileNames[i]));
            LineIterator lineIterator = null;
            try {
                lineIterator = FileUtils.lineIterator(fileList.get(i), "UTF-8");
                while (lineIterator.hasNext()) {
                    String line = lineIterator.nextLine();
                    String[] args = StringUtils.split(line, ",");
                    if (args.length != 3) {
                        continue;
                    }
                    if (absolutePathFileNames[i].contains("people.csv")) {
                        this.peopleMap.put(args[1],
                                new People(
                                        args[0],
                                        args[1],
                                        Integer.parseInt(args[2])));
                    }
                    if (absolutePathFileNames[i].contains("relationships.csv")) {
                        this.relationshipList.add(
                                new Relationship(
                                        new String[]{args[0], args[2]},
                                        args[1]));
                    }
                }
            } catch (IOException io) {
                io.printStackTrace();
            }
            LineIterator.closeQuietly(lineIterator);
        }
    }

    // 3rd of 3 methods created to solve Exercise 1
    public void buildGraph() {
        for (Relationship r : this.relationshipList) {
            this.graph.addEdge(
                    peopleMap.get(r.getEmailPersons()[0]),
                    peopleMap.get(r.getEmailPersons()[1]),
                    r);
        }
    }

    // Method created to solve Exercise 2
    public int peopleLoaded() {
        return peopleMap.size();
    }

    // Method created to solve Exercise 3
    public int relationshipsByNamePeople(String namePerson) {
        int numberEdges = 0;
        People person = identifyPersonInGraph(namePerson);
        if (person != null) {
            numberEdges = this.graph.inEdges(person).size();
        }
        return numberEdges;
    }

    // 1st of 3 methods created to solve exercise 4
    public int sizeExtendedFamily(String name) {
        People person = identifyPersonInGraph(name);
        Set<Relationship> relationshipSet = new HashSet<Relationship>();
        Set<People> visitedNodes = new HashSet<People>();
        Set<People> familyMembers = new HashSet<People>();
        addFamilyRelationship(relationshipSet, familyMembers, visitedNodes, person);
        return familyMembers.size();
    }

    // 2nd of 3 methods created to solve exercise 4
    public People identifyPersonInGraph(String name) {
        Set<People> listNodes = this.graph.nodes();
        for (People singlePerson : listNodes) {
            People person = singlePerson;
            if (person.getName().equals(name)) {
                return person;
            }
        }
        return null;
    }

    // 3rd of 3 methods created to solve exercise 4
    public void addFamilyRelationship(Set<Relationship> relationshipSet, Set<People> familyMembers, Set<People> visitedNodes, People person) {
        if (visitedNodes.add(person)) {
            familyMembers.add(person);
            for (People adjacentNode : this.graph.adjacentNodes(person)) {
                Relationship edge = this.graph.edgesConnecting(person, adjacentNode).iterator().next();
                if (!relationshipSet.contains(edge) && edge.getRelationship().equals("FAMILY")) {
                    relationshipSet.add(edge);
                    addFamilyRelationship(relationshipSet, familyMembers, visitedNodes, adjacentNode);
                }
            }
        }
    }
}
