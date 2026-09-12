# Giles
Personal AI Assistant. Designed with and Intention Based Classifier to determine desire of the query and a Generative Sequence Model to respond. As of yet, it is nowhere near done. The goal is to code the whole thing from scratch, without using external, custom libraries. I have already written a feedforward neural network that can be customised to any size. If you have an idea that you think would help or would like to collaborate, reach out!

#Liscence
At this time, there are no issued licenses to reuse this project. Please reach out if you want to negotiate usage rights. Please credit me if used in other projects.

#Classes
- Giles - The main class for this project. In the end, it will mostly be a loop running inputs and ouptuts between the user and backend.
  - main(String[] args) - main method
- Intents - Enum to record all possible intents for the IBC.
- NeuralNetwork - Pretty self explanatory. Customizable number of layers as well as number of nodes in each layer.
- Matrix - Handles all the matrix math for this project. Oh my goodness there is a lot. It has been stripped down to bare bones otherwise I would be here until the sun exploded making more options for matrix math.
- Optimus Prime - Does the needed text vectorisation. Sentence transformers were the base of this portion, but I simplified the idea a bit to not split words, but process them whole.
- Console - console style text input/output system.
More will come, but this is more of an experiment than anything else.
