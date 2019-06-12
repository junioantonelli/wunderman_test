package com.wundermancommerce.interviewtests.graph;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/** Class created to solve test send by Wunderman Thompson Commerce
 *
 * As requested, this is a simple test class created to solve 4 exercises.
 *
 * The main implementation class GraphStructure has 3 methods to solve Exercise 1, 1 method for Exercise 2,
 * 1 method to Exercise 3 and 3 methods to Exercise 4, total 8 methods, and
 * 3 data structures: the main one, a graph built using MutableNetwork provided
 * by Guava API, and two others auxiliary structures, a Map and a List.
 *
 * We have 2 model classes to represent People and Relationship entities.
 *
 * Methods and variables were named to make the code more readable and avoid unnecessary comments.
 * Methods were modularized as much as possible in order to reach a clean code.
 */
public class GraphStructureTest {
    GraphStructure graphStructure;

    // Solve Exercise 1
    @Before
    public void preparingGraph() {
        this.graphStructure = new GraphStructure();
        List<String> absPath = this.graphStructure.generateAbsolutePaths("people.csv,relationships.csv");
        this.graphStructure.loadFiles(absPath);
        this.graphStructure.buildGraph();
    }

    // Solve Exercise 2
    @Test
    public void validateNumberPeopleLoaded() {
        int expectedPeopleLoaded = 12;
        int actualPeopleLoaded = this.graphStructure.peopleLoaded();
        Assert.assertTrue(expectedPeopleLoaded == actualPeopleLoaded);
    }

    // Solve Exercise 3
    @Test
    public void validateRelationshipPeopleLoaded() {
        String[] listPeople = new String[]{"Bob", "Jenny", "Nigel", "Alan"};
        int[] expectedRelationships = new int[]{4, 3, 2, 0};
        int[] actualRelationships = new int[4];
        for (int i = 0; i < actualRelationships.length; i++) {
            actualRelationships[i] = this.graphStructure.relationshipsByNamePeople(listPeople[i]);
        }
        Assert.assertArrayEquals(expectedRelationships, actualRelationships);
    }

    // Solve Exercise 4
    @Test
    public void validateSizeExtendedFamily() {
        String[] names = new String[]{"Jenny", "Bob"};
        int[] expectedSizeFamily = new int[]{4, 4};
        int[] actualSizeFamily = new int[]{graphStructure.sizeExtendedFamily(names[0]),
                graphStructure.sizeExtendedFamily(names[1])};
        Assert.assertArrayEquals(expectedSizeFamily, actualSizeFamily);
    }
}
