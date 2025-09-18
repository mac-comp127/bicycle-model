# Self-Evaluation: Cell Absorption

## Checklist

### Correctness

- [ ] A `Cell` class is created
- [ ] The `Cell` class has a constructor
- [ ] Proper methods and instance variables are moved from `CellSimulation` into `Cell`:
  - [ ] A CellSimulation has a canvas, a random number generator, a list of cells, and **no** other instance variables
  - [ ] A `Cell` has a shape, radius, and direction, and **no** other instance variables. The `Cell` class also includes two static final constants.
  - [ ] The only methods remaining in `CellSimulation` deal with creating and managing the canvas and the whole list of cells
  - [ ] There are **zero** mentions of `CellSimulation` anywhere in `Cell.java`
- [ ] When running, you should see an animation of many cells moving and growing
- [ ] After a final set of changes, your cells are now absorbing each other!

# Code Style

Check these items from the [Comp 127 Style Guide](https://comp127.innig.net/resources/style-guide/):

- [ ] all classes are in packages
- [ ] package names start with a lowercase letter
- [ ] newly created Java files have a header comment with (a) author name, (b) brief description of class, and (c) acknowledgement, if appropriate
- [ ] identifier (variable and method) names are descriptive
- [ ] **variable** and **method** names are in lowerCamelCase (no CapitalizedNames,  names\_with\_underscores)
- [ ] **class** names are singular nouns
- [ ] **class** names are in UpperCamelCase
- [ ] all instance variables are **private**
- [ ] proper indentation:
  - [ ] opening curly braces (“{”) are at the end of the line
  - [ ] closing curly braces (“}”) are on their own line
  - [ ] the indentation of closing braces is the same as the indentation of the opening statements they match
  - [ ] lines are indented according to how deeply they are nested
- [ ] completed TODO comments are deleted
- [ ] extra blank lines are deleted
- [ ] unneeded commented lines of code are deleted
- [ ] print statements used for testing are deleted

# Reflection

Briefly reflect, in writing, on your experience solving this exercise.  What did you miss?  What did you wish you did better?  What challenges did you face, and how did you overcome them?

## Optional

Related to the challenges above: think of one or two nontrivial mistakes you made
and later fixed
while doing this. Why did you make that mistake? What did you learn from
fixing it? Are you sure you won't make that kind of mistake again?
