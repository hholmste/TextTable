@Category(Collection) class TextTable {

    def symbols = [
            internal_horizontal: '-',
            internal_vertical: '|',
            internal_cross: '+',
            internal_t: '-',
            internal_inverse_t: '-',
            external_horizontal: '=',
            external_vertical: '|',
            external_cross: '#',
            external_t: '=',
            external_inverse_t: '=',
            external_upper_left: '=',
            external_lower_left: '=',
            external_upper_right: '=',
            external_lower_right: '='
    ]

    public void printAsTable(OutputStream target = System.out) {
        target.println ""

        def fields = declaredInstanceFields first()

        def rows = []
        each { object ->
            def objectMap = [:]
            fields.each { field ->
                objectMap["$field"] = asString object."$field"
            }
            rows << objectMap
        }

        def fieldsMeta = [:]
        int maxWidth = 0
        fields.each { field ->
            fieldsMeta["$field"] = [:]
            def width = Math.max(field.length(), rows.max { row -> row."$field".size() }["$field"].size())
            maxWidth += width
            fieldsMeta["$field"]["width"] = width
        }

        maxWidth += (3 * fields.size()) + 1

        println "-" * maxWidth
        print "|"
        fields.each { field ->
            int width = fieldsMeta["$field"]["width"]
            printf " %${width}s |", field
        }
        println ""
        println "-" * maxWidth


        rows.each { row ->
            print "|"
            fields.each { field ->
                int width = fieldsMeta["$field"]["width"]
                printf " %${width}s |", row."$field"
            }
            println ""
        }

        println "-" * maxWidth
    }

    static String asString(object) {
        object == null ? "" : object.toString()
    }

    static Collection<String> declaredInstanceFields(object) {
        object.class.declaredFields.grep { !it.isSynthetic() }.collect { it.name }
    }

}
