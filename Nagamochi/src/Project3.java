import java.net.*;
import java.util.*;
import org.jgrapht.*;
import org.jgrapht.ext.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;
import org.jgrapht.graph.DefaultEdge;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import org.jgraph.*;
import org.jgraph.graph.*;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.BellmanFordShortestPath;
import org.jgrapht.alg.KShortestPaths;
import org.jgrapht.alg.StoerWagnerMinimumCut;
import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.graph.SimpleWeightedGraph;
public class Project3 {
 static int node;
 static double network_Reliability[][];
 static int edge_num = 0;
 static double product = 1;
 static int temp1 = 0;
 static double sum = 0; 
 static double i=0.02;
 static ArrayList<DefaultEdge> defaultEdge = new ArrayList<DefaultEdge>();


 
 
 
 public static void Ex_enum() {
 HashMap<char[], Boolean> hashMap = new HashMap<char[], Boolean>();
 HashMap<char[], Double> sumMap = new HashMap<char[], Double>();
 for (int i = 0; i < Math.pow(2, edge_num); i++) {
 char[] c = Integer.toBinaryString(i).toCharArray();
 char[] c1 = new char[edge_num];
 if (c.length < edge_num) {
 for (int j = 0; j < (edge_num - c.length); j++) {
 c1[j] = '0';
 }
 for (int j = edge_num - c.length; j < edge_num; j++) {
 c1[j] = c[j + (c.length - edge_num)];
 }
 } else {
 c1 = c;
 }
 if (isPath(c1)) {
 hashMap.put(c1, true);
 sumMap.put(c1, product); 
 sum += sumMap.get(c1);
 } else { 
 }
 }
//System.out.println(sum);
 System.out.println("Reliability using Exhaustive enumeration for p["+i+"] is: " + sum);
 }

 public static void setDegree(double r){
	 for(int i=0; i< node ;i++){
		 for(int j=0;j<node ;j++){
			 network_Reliability[i][j]=0; 
		 }
		 }
	 edge_num=0;
 for(int i=0; i< 5 ;i++){
	 for(int j=0;j<5 ;j++){
		 if(i>j)
		 {
			 network_Reliability[i][j]=r;
			 edge_num++;
		 }
		 else
		 {
			 network_Reliability[i][j]=0;  
		 }
		 
	 }
 }
// System.out.println("No. of Edges : " +edge_num);

 }
 
 public static boolean notvalidpath(char[] c1){
 
 UndirectedGraph<String, DefaultEdge> stringGraph = 
createStringGraph(network_Reliability);
 UndirectedGraph<String, DefaultEdge> stringGraphFull = 
createStringGraph(network_Reliability);
 for(int i=0; i<c1.length; i++){
 if(c1[i]=='0'){
 stringGraph.removeEdge(defaultEdge.get(i));
 }
 } ConnectivityInspector< String, DefaultEdge> conInspector = new 
ConnectivityInspector<String, DefaultEdge>(stringGraph);
 if(!conInspector.pathExists(String.valueOf(0), String.valueOf(node-1))){
 return false;
 }
 return true;
 }
 //</editor-fold>
 public static boolean isPath(char[] c1){

 sum=0;
 product=1;
 UndirectedGraph<String, DefaultEdge> stringGraph = 
createStringGraph(network_Reliability);
 UndirectedGraph<String, DefaultEdge> stringGraphFull = 
createStringGraph(network_Reliability);
 for(int i=0; i<c1.length; i++){
 if(c1[i]=='0'){
 stringGraph.removeEdge(defaultEdge.get(i));
 }
 }
 ConnectivityInspector< String, DefaultEdge> conInspector = new 
ConnectivityInspector<String, DefaultEdge>(stringGraph);
 if(!conInspector.pathExists(String.valueOf(0), String.valueOf(node-1))){
 return false;
 }
 else{
 Set<DefaultEdge> edgeSet = stringGraphFull.edgeSet();
 Iterator iterator = edgeSet.iterator();
 while (iterator.hasNext()) {
 DefaultEdge dE = (DefaultEdge)iterator.next();
 String[] s = dE.toString().substring(1, 6).split(":");
 Random randomGenerator = new Random();
 int randomInt = randomGenerator.nextInt(10);
 if(defaultEdge.contains(dE)){
	product = product*network_Reliability[Integer.parseInt(s[0].trim())]
		[Integer.parseInt(s[1].trim())];
}
 else{
 product = product*(1 - network_Reliability[Integer.parseInt(s[0].trim())]
[Integer.parseInt(s[1].trim())]);
 System.out.println("a "+product);
 }
 }
 }
 return true; 
 }
 
 private static WeightedGraph<String, DefaultEdge> createDirectedStringGraph(double[]
[] rMatrix)
 {
 WeightedGraph<String, DefaultEdge> g = new SimpleWeightedGraph<String, 
DefaultEdge>(DefaultEdge.class);
 temp1 = 0;
 // add the nodes
 while(temp1<node){
 g.addVertex(String.valueOf(temp1));
 temp1++;
 }
  // Creating a Network by adding nodes
 for(int i=0; i< node ;i++){
 for(int j=0;j<node ;j++){
 if(rMatrix[i][j]!=0){
 defaultEdge.add(g.addEdge(String.valueOf(i),String.valueOf(j)));
 }
 }
 }
 
 return g;
 } 
 private static UndirectedGraph<String, DefaultEdge> createStringGraph(double[][] rMatrix)
 {
 UndirectedGraph<String, DefaultEdge> g = new SimpleGraph<String, 
DefaultEdge>(DefaultEdge.class);
 temp1 = 0;
 // adding nodes
 while(temp1<node){
 g.addVertex(String.valueOf(temp1));
 temp1++;
 }
 // add edges to create a network
 for(int i=0; i< node ;i++){
 for(int j=0;j<node ;j++){
 if(rMatrix[i][j]!=0){
 defaultEdge.add(g.addEdge(String.valueOf(i),String.valueOf(j)));
 }
 }
 }
 return g;
 } 
 public static void main(String[] args) {
 Scanner scanner = new Scanner(System.in);
 System.out.println("Enter the number of Nodes in the network: ");
 node = scanner.nextInt();
 network_Reliability = new double[node][node];
while(i<=1){
 setDegree(i);
 i=i+0.02;
 //System.out.println(i);
 Ex_enum();
 }
}
}
