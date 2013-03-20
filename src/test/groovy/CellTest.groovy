import spock.lang.*

class CellTest extends Specification {

    def "a cell"() {
        new Cell(content: "TestText")
    }

    def "when formatting a text that has the same length as desired, no formatting should occur"() {
        given:
        Cell c = "a cell"()

        when:
        c.desiredWidth = 8

        then:
        c.formattedContent == "TestText"
    }

    def "when formatting a text that shorter than desired, the text should be justified"() {
        given:
        Cell c = "a cell"()

        when:
        c.desiredWidth = 10

        then:
        c.formattedContent == "TestText  "
    }

    def "when formatting a text that shorter than desired, and justification is RIGHT the text should be right-justified"() {
        given:
        Cell c = "a cell"()
        c.desiredWidth = 10

        when:
        c.justification = Cell.Justification.RIGHT

        then:
        c.formattedContent == "  TestText"
    }

    def "when formatting a text that shorter than desired, and justification is CENTER the text should be center-justified"() {
        given:
        Cell c = "a cell"()
        c.desiredWidth = 10

        when:
        c.justification = Cell.Justification.CENTER

        then:
        c.formattedContent == " TestText "
    }

    def "when formatting a text that shorter than desired, justification is CENTER, and the leftover is odd, formatting should favour the left side"() {
        given:
        Cell c = "a cell"()
        c.desiredWidth = 11

        when:
        c.justification = Cell.Justification.CENTER

        then:
        c.formattedContent == " TestText  "
    }

    def "when formatting a text that is longer than desired, the spillover should be replaced by ellipsis"() {
        given:
        Cell c = "a cell"()

        when:
        c.desiredWidth = 7

        then:
        c.formattedContent == "Test..."
    }

    def "when desired width is very small, only ellipsis should be displayed"() {
        given:
        Cell c = "a cell"()
        c.borderLeft = "|"
        c.borderRight = "|"

        when:
        c.desiredWidth = 1

        then:
        c.formattedContent == "|...|"
    }

    def "when formatting text with borders, the borders should also be returned"() {
        given:
        Cell c = "a cell"()
        c.desiredWidth = 10
        c.justification = Cell.Justification.CENTER

        when:
        c.borderLeft = "¤"
        c.borderRight = "!"

        then:
        c.formattedContent == "¤ TestText !"
    }

}
