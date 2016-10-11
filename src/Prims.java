import java.util.Scanner;

public class Prims {
	private boolean isVisit[];
	private boolean isNotVisit[];
	private int numberOfVertices;
	private float adjMatrix[][];
	private float key[];
	private int parent[];
	

	public static void main(String[] arg) { //메인함수
		float adjM[][];
		int numOfVertices;
		Scanner scan = new Scanner(System.in);
		float pointx[];
		float pointy[];
		
			System.out.println("점의 개수를 입력해주세요.");
			numOfVertices = scan.nextInt();
			adjM = new float[numOfVertices + 1][numOfVertices + 1];
			pointx = new float[numOfVertices+1];
			pointy = new float[numOfVertices+1];
			
			for (int i = 0; i < numOfVertices; i++) {
				System.out.println(i+1 +"번째점을 입력하세요");
				
				pointx[i+1] = scan.nextInt();
				pointy[i+1] = scan.nextInt();
			}

			
			
			System.out.println("입력한 점을 통한 가중치 행렬");
			for (int i = 1; i <= numOfVertices; i++) {
				for (int j = 1; j <= numOfVertices; j++) {
					adjM[j][i] = Math.abs((float) Math.sqrt((pointx[j]-pointx[i])*(pointx[j]-pointx[i])+(pointy[j]-pointy[i])*(pointy[j]-pointy[i]))) ;
					
					if (i == j) {
						adjM[i][j] = 0;
						continue;
					}
				}
			}

			Prims prims = new Prims(numOfVertices);
			prims.primsAlgorithm(adjM);
			prims.printMST();

	}
	
	
	public Prims(int verticeNum) {
		this.numberOfVertices = verticeNum;
		isVisit = new boolean[verticeNum + 1];
		isNotVisit = new boolean[verticeNum + 1];
		
		adjMatrix = new float[verticeNum + 1][verticeNum + 1];
		key = new float[verticeNum + 1];
		parent = new int[verticeNum + 1];
	}
	
	public int getisVisitCount(boolean isVisit[]) {
		int count = 0;
		for (int i = 0; i < isVisit.length; i++) {
			if (isVisit[i]) {
				count++;
			}
		}
		return count;
	}

	public void primsAlgorithm(float[][] adjM) 
	{
		
		int evaluationVertex;
		for (int source = 1; source <= numberOfVertices; source++) {
			for (int destination = 1; destination <= numberOfVertices; destination++) {
				adjMatrix[source][destination] = (float)adjM[source][destination];
			}
		}

		for (int index = 1; index <= numberOfVertices; index++) {
			key[index] = 999;
		}
		key[1] = 0;
		isVisit[1] = true;
		parent[1] = 1;

		while (getisVisitCount(isVisit) != 0) {
			evaluationVertex = getMimumKeyVertexFromisVisit(isVisit);
			isVisit[evaluationVertex] = false;
			isNotVisit[evaluationVertex] = true;
			evaluateNeighbours(evaluationVertex);
		}
	}

	private int getMimumKeyVertexFromisVisit(boolean[] isVisit2) {
		float min = Integer.MAX_VALUE;
		int node = 0;
		for (int vertex = 1; vertex <= numberOfVertices; vertex++) {
			if (isVisit[vertex] == true && key[vertex] < min) {
				node = vertex;
				min = key[vertex];
			}
		}
		return node;
	}

	public void evaluateNeighbours(int evaluationVertex) {
		for (int destinationvertex = 1; destinationvertex <= numberOfVertices; destinationvertex++) {
			if (isNotVisit[destinationvertex] == false) {
				if (adjMatrix[evaluationVertex][destinationvertex] != 999) {
					if (adjMatrix[evaluationVertex][destinationvertex] < key[destinationvertex]) {
						key[destinationvertex] = adjMatrix[evaluationVertex][destinationvertex];
						parent[destinationvertex] = evaluationVertex;
					}
					isVisit[destinationvertex] = true;
				}
			}
		}
	}

	public void printMST() { //출력
		for (int i = 1; i <= numberOfVertices; i++) 
		{
			for (int j = 1; j <= numberOfVertices; j++) 
			{
				System.out.print(adjMatrix[i][j]+"\t"+"\t");
			}
			System.out.println("");
		}
		float sum = 0;
		
		System.out.println("시작점  :\t 도착점 = \t가중치");
		for (int vertex = 2; vertex <= numberOfVertices; vertex++) {
			System.out.println
			(parent[vertex] + "\t:\t" + vertex + "\t=\t"
		+ adjMatrix[parent[vertex]][vertex]);
		
			sum +=  adjMatrix[parent[vertex]][vertex];
			
		}
		System.out.println("해당 좌표의 최단거리 : " + sum );
	}

}
