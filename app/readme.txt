Run the program with `gradle run`.

PoolFactory is the interface for the factory method which concrete factories implement.
This interface contains the create() method which takes in a JSONObject and creates/returns a PoolObject
The factories all produce the interface PoolObject which all the concrete Table and Ball implement.
TableFactory and BallFactory are concrete implementations of the PoolFactory and are capable of producing
concrete objects Table and Ball respectively.

Builder is the interface of the Builder pattern for BallBuilder and contains all the methods necessary to build
a ball or a ball related object.
BallFactory acts as the director for the BallBuilder and builds all the part of the ball according to the JSON file.
The BallBuilder is a concrete implementation of the builder and allows the parts of the ball to be built one by one.

Hole strategy is the interface of the strategy pattern, it contains the enterHole and reset behaviour for the balls.
WhiteBallHoleStrategy, BlueBallHoleStrategy, RedBallHoleStrategy are concrete implementations of the interface and
define the behaviour of the corresponding balls. They are stored as a field in the Ball class and allow the behaviour
of Ball to change accordingly.