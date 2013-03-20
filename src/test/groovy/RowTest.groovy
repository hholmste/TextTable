import spock.lang.Specification

class RowTest extends Specification {

    def "an equidistant row should layout its cells with the same number of characters"() {
        given:
        Row r = new Row()
        r.content = ["This", "is", "some kind", "of content"]

        when:
        r.layout = Row.LayoutStyle.EQUIDISTANT_BORDERS
        r.desiredWidth = 28

        then:
        r.formattedContent == "This   is     some...of c..."
    }

    def "a row should use the right borders for its cells"() {
        given:
        Row r = new Row()
        r.content = ["This", "is", "some kind", "of content"]
        r.layout = Row.LayoutStyle.EQUIDISTANT_BORDERS
        r.desiredWidth = 28

        when:
        r.externalBorder = "§"
        r.internalBorder = "|"

        then:
        r.formattedContent == "§This   |is     |some...|of c...§"
    }

}
