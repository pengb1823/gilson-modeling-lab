package project1;

import java.util.*;
import java.io.*; 

import java.lang.Math;
public class LangevinAlgorithm {
	
	private double baseGamma, baseTemp, b, timestep, steps;
	private int[] nAtoms;
	private double[][] coord;
	private double[][] momentumPrime; // temp holding array for preserving the last timestep's momentum for calculation of force
	private double[][] momentum;
	private double[][] force;
	private double avogadro = 6.0221409e23;
	private ArrayList<Particle> particleList;
	//List<Double> speedList = new ArrayList<Double>();
	//List<Double> kineticEList = new ArrayList<Double>();
	Random ran = new Random(); 
	
	// constructors
	public LangevinAlgorithm() {
		//TODO: add default constructor
	}

	public LangevinAlgorithm(double[] m, double[] ep, double[] sig, double gam, double B, double t, double time, /*double thrust,*/int[] n, double nStep) {
		nAtoms = new int[3];
		nAtoms = n.clone();
		
		baseGamma = gam;
		baseTemp = t;
		b = B;
		timestep = time;
		steps = nStep;
		setMomentum();
		setPrime();
		//creates a particle list where each particle has its own coord
		particleList = new ArrayList<>();

		for (int i = 0; i < n[1]; i++) {
			particleList.add(new Particle("substrate", m[2], ep[2], sig[2], gam, getRandomCoord()));
		}
		for (int i = 0; i < n[0]; i++) {
			particleList.add(new Particle("enzyme", m[0], ep[0], sig[0], gam, getRandomCoord()));
		}
		for (int i = 0; i < n[0]; i++) {
			particleList.add(new Particle("active", m[1], ep[1], sig[1], gam, getRandomCoord()));
		}

		//0 position = enzyme #, 1 position = substrate #

	}
	
	public double[] getRandomCoord() {
		double[] coord = new double[3];
		Random rand = new Random();
		for (int i = 0; i < 3; i++) {
			coord[i] = -1e-8 + (1e-8 - (-1e-8)) * rand.nextDouble();
		}
		System.out.println(Arrays.toString(coord));
		return coord;
	}
	
	public void setRandomCoord() {
		for (int i = 0; i < nAtoms[1]; i++) {
			particleList.get(i).setCoord(getRandomCoord());
		}
	}
	
	public double[][] getPresetCoord() {
		double[][] coord = {{0.0, 0.0, 0.0}, {0.0, 0.0, 0.1}, {0.1, 0.2, -0.3}};
		return coord;
	}
	
	public double getLJForce(double r, double vector, double epsilon, double sigma) {
		if (r != 0.0) {
			return ((48 * epsilon)/(r*r)) * ((Math.pow((sigma / r), 12)) - .5*(Math.pow((sigma / r), 6))) * vector;
		}
		else return 0.0;
	}
	
	public double[][] getForce() {
		double[][] force = new double[nAtoms[1]][3];
		double[] distanceVector = new double[3];
		double r;
		
		for (int i = 0; i < nAtoms[1]; i++) {
			force[i][0] = 0.0;
			force[i][1] = 0.0;
			force[i][2] = 0.0;
			
			for (int j = 0; j < nAtoms[1]; j++) {
				/*
				distanceVector[0] = coord[i][0] - coord[j][0];
				distanceVector[1] = coord[i][1] - coord[j][1];
			    distanceVector[2] = coord[i][2] - coord[j][2];
			    
			    r = Math.sqrt(Math.pow(distanceVector[0], 2) + Math.pow(distanceVector[1], 2) + Math.pow(distanceVector[2], 2));
			    */

			    
			    
			    
			    // TODO: fix LJ forces, make sure this works
			    /*
			    force[i][0] += getLJForce(r, distanceVector[0]);
			    force[i][1] += getLJForce(r, distanceVector[1]);
			    force[i][2] += getLJForce(r, distanceVector[2]);
			    */
			    
			    /*
			    double forceMagnitude = Math.sqrt(Math.pow(force[i][0], 2) + Math.pow(force[i][1], 2) + Math.pow(force[i][2], 2)); // finds the magnitude of force for the atom
			    if (Math.random() * 10 >= coord[0][i]) {
			    	force[i][0] += force[i][0] * thrust / forceMagnitude;
			    	force[i][1] += force[i][1] * thrust / forceMagnitude;
			    	force[i][2] += force[i][2] * thrust / forceMagnitude;
			    { 
			    */
			    
			}
		}
		//scales LJ forces appropriately
		/*
		for (int i = 0; i < nAtoms; i++) {
			for (int j = 0; j < 3; j++) {
				force[i][j] /= 1e65;
			}
		}
		*/
		//System.out.println(Arrays.deepToString(force));
		return force;
	}
	
	public double[][] getMomentum() {
		double[][] momentum = new double[nAtoms[1]][3];
		for (int i = 0; i < nAtoms[1]; i++) {
			for (int j = 0; j < 3; j++) {
				momentum[i][j] = 0.0;
			}
		}
		return momentum;
	}
	
	public double[][] getPrime() {
		double[][] momentumPrime = new double[nAtoms[1]][3];
		for (int i = 0; i < nAtoms[1]; i++) {
			for (int j = 0; j < 3; j++) {
				momentumPrime[i][j] = 0.0;
			}
		}
		return momentumPrime;
	}
	
	public void setMomentum() {
		momentum = getMomentum();
	}
	
	public void setForce() {
		force = getForce();
	}
	
	public void setPrime() {
		momentumPrime = getPrime();
	}
	
	public void setSpeed(double coord[][], int nAtoms) { //sums up the magnitudes of speed and appends to the ArrayList speed
		double speed = 0.0;
		for (int i = 0; i < nAtoms; i++) {
			for (int j = 0; j < 3; j++) {
				speed += Math.sqrt(Math.pow(coord[i][0], 2) + Math.pow(coord[i][1], 2) + Math.pow(coord[i][2], 2));
			}
			//speedList.add(speed);
			//kineticEList.add(0.5*speed*speed*mass);
			//speed = 0.0;
		} 
	}
	
	public void setNewCoord() {
		for (int i = 0; i < nAtoms[1]; i++) {
			//setForce();
			//TODO: Fix forces w new coord system
			//force = getForce();
			for (int j = 0; j < 3; j++) {
				//restrains particles to a box of certain dimensions, assuming box collision is completely elastic
				if(particleList.get(i).getCoord()[j] > 1e-6) {
					particleList.get(i).setCoordAtPos(1e-6, j);
                	momentum[i][j] = -momentum[i][j];
                }
                
                else if(particleList.get(i).getCoord()[j] < -1e-6) {
					particleList.get(i).setCoordAtPos(-1e-6, j);
                	momentum[i][j] = -momentum[i][j];
                }
				double gamma = baseGamma;//baseGamma * ((coord[i][0] + 1e-6) /2e-6 + 1);
				double temp = baseTemp;//(coord[i][0] + 1e-6) /2e-6 * 100 + 250;
				//langevin dynamics algorithm -- taken from Allen and Tildesley: Computer Simulation of Liquids
				momentum[i][j] = momentum[i][j] + 0.5 * timestep * force[i][j];
				particleList.get(i).setCoordAtPos(particleList.get(i).getCoord()[j] + 0.5 * timestep * momentum[i][j] / particleList.get(i).getMass(), j);
                //coord[i][j] = coord[i][j] + 0.5 * timestep * momentum[i][j] / mass;
                momentumPrime[i][j] = Math.exp(-gamma * timestep) * momentum[i][j] + Math.sqrt(1 - Math.exp(-2 * gamma * timestep)) * Math.sqrt(particleList.get(i).getMass() * b * temp) * (ran.nextGaussian());
				particleList.get(i).setCoordAtPos(particleList.get(i).getCoord()[j] + 0.5 * timestep * momentum[i][j] / particleList.get(i).getMass(), j);
				particleList.get(i).setCoordAtPos(particleList.get(i).getCoord()[j] + .5 * timestep * momentumPrime[i][j] / particleList.get(i).getMass(), j);
                //coord[i][j] = coord[i][j] + .5 * timestep * momentumPrime[i][j] / mass;
                //force = getForce();
                momentum[i][j] = momentumPrime[i][j]  + .5 * timestep * force[i][j];
                
                if(particleList.get(i).getCoord()[j] > 1e-6) {
					particleList.get(i).setCoordAtPos(1e-6, j);
                	momentum[i][j] = -momentum[i][j];
                }
                
                else if(particleList.get(i).getCoord()[j] < -1e-6) {
					particleList.get(i).setCoordAtPos(-1e-6, j);
                	momentum[i][j] = -momentum[i][j];
                }
			}

			//kineticEList.add(0.5*mass*Math.sqrt(Math.pow(momentum[i][0]/mass, 2) + Math.pow(momentum[i][1]/mass, 2) + Math.pow(momentum[i][2]/mass, 2)));
		}
		
		/*for (int i = 0; i < nAtoms; i++) {
			for (int j = 0; j < 3; j++) {
				setForce();
				momentum[i][j] = momentumPrime[i][j]  + .5 * timestep * force[i][j];
			}
		}*/
	}
	
	

	
	public void setPresetCoord() {
		coord = getPresetCoord();
	}
	
	//prints in .xyz file format
	public void printCoord() throws FileNotFoundException {
		File f = new File("C:\\Users\\lochn\\OneDrive\\Documents\\output\\output" + (int)baseTemp + ".xyz");
		//sets up the printing of all the x positions to files
		/*
		ArrayList<PrintStream> streamList = new ArrayList<>();
		for (int i = 0; i < nAtoms[1]; i++) {
			streamList.add(new PrintStream("C:\\Users\\lochn\\OneDrive\\Documents\\output\\xpos" + i + ".txt"));
		}
		*/
		PrintStream p = new PrintStream(f);
		p.flush();
		// sets output to file
		System.setOut(p); 
		//PrintStream console = System.out;
		setMomentum();
		setForce();
		double count = 0.0;
		for (double k = 0.0; k < steps; k += 1.0) {
			System.setOut(p);
			//skips 4/5 frames
			if (k % 2000 == 0) {
				System.out.println(nAtoms[1]);
				System.out.println(count);
				count += 1.0;
				//System.out.println(k);
				for (int i = 0; i < nAtoms[1]; i++) {
					//System.setOut(streamList.get(i));
					//System.out.println(particleList.get(i).getCoordAtPos(0));
							//coord[i][0]);
					//System.setOut(p);
					System.out.print(particleList.get(i).getType());
					for (int j = 0; j < 3; j++) {
						System.out.print(" " + (particleList.get(i).getCoordAtPos(j)*10e7));
						if ((j + 1) % 3 == 0) System.out.print("\n");
					}
				}
				//setSpeed(coord, nAtoms);
			}
			setNewCoord();
		}
		//double total = 0.0;
		/*for (int i = 0; i < kineticEList.size(); i++) {
			total += kineticEList.get(i);
		}
		double avg = total / steps;
		System.out.println(avg); */
		//System.setOut(console); // sets output back to console and prints when done
		//System.out.println("Done!");
	}
	
	
	
}
