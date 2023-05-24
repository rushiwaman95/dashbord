#include <mpi.h>
#include <stdio.h>
#include <stdlib.h>

#define ARRAY_SIZE 16

int main(int argc, char** argv) {
  int rank, size;
  int sum = 0;
  int array[ARRAY_SIZE];

  // Initialize MPI
  MPI_Init(&argc, &argv);
  MPI_Comm_rank(MPI_COMM_WORLD, &rank);
  MPI_Comm_size(MPI_COMM_WORLD, &size);

  // Populate the array on the root process
  if (rank == 0) {
    for (int i = 0; i < ARRAY_SIZE; i++) {
      array[i] = i + 1;
    }
  }

  // Scatter the array to all processes
  int subarray_size = ARRAY_SIZE / size;
  int subarray[subarray_size];
  MPI_Scatter(array, subarray_size, MPI_INT, subarray, subarray_size, MPI_INT, 0, MPI_COMM_WORLD);

  // Sum the local elements
  int local_sum = 0;
  for (int i = 0; i < subarray_size; i++) {
    local_sum += subarray[i];
  }

  // Display the local sum of each process
  printf("Process %d local sum is %d\n", rank, local_sum);

  // Reduce the local sums to get the final sum on the root process
  MPI_Reduce(&local_sum, &sum, 1, MPI_INT, MPI_SUM, 0, MPI_COMM_WORLD);

  // Print the result on the root process
  if (rank == 0) {
    printf("The sum of the elements is %d\n", sum);
  }

  // Finalize MPI
  MPI_Finalize();
  return 0;
}


// **********Assignment 3**********

// OS Required : Ubuntu
// Softwares Required - gcc compiler,mpi library

// Procedure:
// 1. Install the gcc compiler on ubuntu if not installed (to check if gcc compiler is installed or not run this command - "gcc --version"). Install the husing the command 
// 	"sudo apt install build-essentials"
// 2. Install the mpi library using the following command - "sudo apt install mpich"
// 3. To execute the file , open the terminal in the folder where the file resides. 
// 4. Execute the following commands one by one - 
// 	i. mpicc arr_sum.c -o mpi_sum
// 	ii. mpirun -np 4 ./mpi_sum
// 5. The output will be displayed.
