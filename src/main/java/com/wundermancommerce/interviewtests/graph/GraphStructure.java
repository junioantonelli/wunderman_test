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
    public List<String> generateAbsolutePaths(String fileName) {
        List<String> fileNames = List.of(fileName.split(","));
        List<String> paths = new ArrayList<String>();
        for(String nameOfFile : fileNames){
            paths.add(getClass().getClassLoader().getResource(nameOfFile).getPath());
        }
        return paths;
    }

    // 2nd of 3 methods created to solve Exercise 1
    public void loadFiles(List<String> absolutePathFileNames) {
        List<File> fileList = new ArrayList<File>();
        for (int i = 0; i < absolutePathFileNames.size(); i++) {
            fileList.add(new File(absolutePathFileNames.get(i)));
            LineIterator lineIterator = null;
            try {
                lineIterator = FileUtils.lineIterator(fileList.get(i), "UTF-8");
                while (lineIterator.hasNext()) {
                    String line = lineIterator.nextLine();
                    List<String> args = List.of(StringUtils.split(line, ","));
                    if (args.size() != 3) {
                        continue;
                    }
                    if (absolutePathFileNames.get(i).contains("people.csv")) {
                        this.peopleMap.put(args.get(1),
                                new People(
                                        args.get(0),
                                        args.get(1),
                                        Integer.parseInt(args.get(2))));
                    }
                    if (absolutePathFileNames.get(i).contains("relationships.csv")) {
                        this.relationshipList.add(
                                new Relationship(
                                        List.of(new String[]{args.get(0), args.get(2)}),
                                        args.get(1)
                                )
                        );
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
                    peopleMap.get(r.getEmailPersons().get(0)),
                    peopleMap.get(r.getEmailPersons().get(1)),
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
