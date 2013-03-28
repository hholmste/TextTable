import spock.lang.Specification

import java.awt.*

class TextTableTest extends Specification {

    public static void main(args) {
        println "test"

        use(TextTable) {
            [new TestObject(name: "Alpha", age: 2, colour: Color.BLACK, make: "Something fancy"),
                    new TestObject(name: "Beta", age: 1, colour: Color.WHITE, make: "An ugly thing"),
                    [name: "Gamma", age: 3, make: "Invisible", armament: "Puny"]]
                    .printAsTable(System.out)
        }
        println "done"

    }

    private static class TestObject {
        def name
        def age
        def colour
        def make
    }
}
