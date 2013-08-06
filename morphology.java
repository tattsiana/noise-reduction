//Tatsiana Tupeka
//morphology project
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class morphology{
  public static int [][]a;
	public static int [][]b;
	public static int [][]c;
	public static int [][]mask={{0,1,0},
				  {1,1,1},
				  {0,1,0}};//structuring element
	public static int [][]mask2={{0,0,0},
		  {1,1,1},
		  {1,1,1}};
	public static int colNum, rowNum, minScale, maxScale;
 
	public static void main( String [ ] args ) throws IOException {
		readFile("input.txt");
		zeroFrame(a);
		b=new int [a.length][a[0].length]; //temporary arrays
		c=new int [a.length][a[0].length];//temporary arrays

		erosion(a, mask,b);
		prettyPrint(b, "erosion.out");// after erosion
		
		dilation(b, mask,c); // opening of a (dialtion of eroded image)
		prettyPrint(c, "opening.out");
		
		b=new int [a.length][a[0].length];
		dilation(c, mask2,b);
		prettyPrint(b, "dilation1.out");
		c=new int [a.length][a[0].length];

		erosion2(b, mask2,c);
		prettyPrint(c, "final.out");
	
	}
	public static void dilation (int [][]init,int [][]m, int [][]res){
		for (int i = 1; i <init.length-1; i++){			
			for (int j = 1; j < init[0].length-1; j++){	
				if (init[i][j]>0){
					for (int r=-1; r<=1; r++){
						for (int k=-1; k<=1; k++){
							if (m[r+1][k+1]>0)
								res[i+r][j+k]=m[r+1][k+1];
						}
					}
					
				}
			}
			
		}		
	}
	public static void erosion (int [][]init,int [][]m, int [][]res){
		for (int i = 1; i <init.length-1; i++){			
			for (int j = 1; j < init[0].length-1; j++){	
				if (init[i][j]>0){
					int count=0;
					for (int r=-1; r<=1; r++){
						for (int k=-1; k<=1; k++){
							if ((init[i+r][j+k]==m[r+1][k+1])&&(init[i+r][j+k]>0))
								count++;
						}
					}
					if (count==5) res[i][j]=1;
						else res[i][j]=0;
				}
			}
			
		}		
	}
	public static void erosion2 (int [][]init,int [][]m, int [][]res){
		for (int i = 1; i <init.length-1; i++){			
			for (int j = 1; j < init[0].length-1; j++){	
				if (init[i][j]>0){
					int count=0;
					for (int r=-1; r<=1; r++){
						for (int k=-1; k<=1; k++){
							if ((init[i+r][j+k]==m[r+1][k+1])&&(init[i+r][j+k]>0))
								count++;
						}
					}
					if (count==6) res[i][j]=1;
						else res[i][j]=0;
				}
			}
			
		}		
	}
	public static void readFile (String fileName) throws IOException{
		FileInputStream fstream = new FileInputStream(fileName);
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String line="";
		line=br.readLine();
		String []numbers = line.split(" ");
		colNum= Integer.parseInt(numbers[1]);
		rowNum =Integer.parseInt(numbers[0]);
		minScale = Integer.parseInt(numbers[2]);
		maxScale = Integer.parseInt(numbers[3]);
		a=new int [rowNum][colNum];

			for (int i = 0; i < rowNum; i++){
				line=br.readLine();
				int l=0;
				for (int j = 0; j < colNum; j++){
						if((l<line.length()-2)&&(line.substring(l, l+2).equals("1 ")))
							a[i][j]=1; 							
						else a[i][j]=0; 				
						l=l+2;
				}
			}

	}
	public static void prettyPrint (int b[][],String name) throws IOException{
		FileWriter fstream1 = new FileWriter(name);
		BufferedWriter out = new BufferedWriter(fstream1);
		out.write(colNum +" "+rowNum+" "+minScale+" "+maxScale);
		out.newLine();
		  for (int i = 0; i < b.length; i++){        		
		 		 for (int j = 0; j < b[0].length; j++){ 
		 			 if (b[i][j]>0){
		 			 	out.write(b[i][j]+" ");
		 				 
		 			 }
		 			 else out.write("  ");
		 		 }
		 		 out.newLine();
		  }
		  out.close();
	}

	public static void zeroFrame(int matrix [][]) {
		int [][]b=new int [matrix[0].length+2][matrix.length+2];
		for (int i = 1; i < matrix[0].length+1; i++){			
			for (int j = 1; j < matrix.length+1; j++){
					b[i][j]=matrix[i-1][j-1];				
			}
		}
		a=b.clone();
	}
}
