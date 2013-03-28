@Category(Collection) class TextTable {

    def static symbols = [
            internal_horizontal: '-',
            internal_vertical: '|',
            internal_cross: '+',
            internal_t: '-',
            internal_inverse_t: '-',
            external_horizontal: '=',
            external_vertical: '§',
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

        Collection<String> fields = allDeclaredInstanceFields this

        def rows = []
        each { object ->
            def objectMap = [:]
            fields.each { field ->
                try {
                    objectMap["$field"] = asString object."$field"
                } catch (MissingFieldException e) {
                    objectMap["$field"] = "N/A"
                }
            }

            rows << objectMap
        }

        def fieldsMeta = [:]
        int maxWidth = 0
        int index = 0
        fields.each { field ->
            fieldsMeta["$field"] = [:]
            def width = Math.max(field.length(), rows.max { row -> row."$field".size() }["$field"].size())
            maxWidth += width
            fieldsMeta["$field"]["width"] = width
            fieldsMeta["$field"]["index"] = index++
        }

        maxWidth += (3 * fields.size()) - 1

        /*
        * prints the headers
        */
        println symbols.external_vertical + symbols.external_horizontal * maxWidth + symbols.external_vertical
        fields.each { field ->
            int width = fieldsMeta["$field"]["width"]
            def leftBorder = fieldsMeta["$field"]["index"] == 0 ? symbols.external_vertical : symbols.internal_vertical
            def rightBorder = fieldsMeta["$field"]["index"] >= fields.size()-1 ? symbols.external_vertical : ""
            printf "$leftBorder %${width}s $rightBorder", field
        }
        println ""
        println symbols.external_vertical + symbols.internal_horizontal * maxWidth + symbols.external_vertical

        /*
        * prints the rows
         */
        rows.each { row ->
            fields.each { field ->
                int width = fieldsMeta["$field"]["width"]
                def leftBorder = fieldsMeta["$field"]["index"] == 0 ? symbols.external_vertical : symbols.internal_vertical
                def rightBorder = fieldsMeta["$field"]["index"] >= fields.size()-1 ? symbols.external_vertical : ""
                printf "$leftBorder %${width}s $rightBorder", row."$field"
            }
            println ""
        }

        println symbols.external_vertical + symbols.external_horizontal * maxWidth + symbols.external_vertical
    }

    static String asString(object) {
        object == null ? "" : object.toString()
    }

    static Collection<String> declaredInstanceFields(object) {
        if (object.class == null) {
            object.keySet()
        } else {
            object.class.declaredFields.grep { !it.isSynthetic() }.collect { it.name }
        }
    }

    static Collection<String> allDeclaredInstanceFields(collection) {
        collection.inject new HashSet<String>(), {
            set, object ->
                set.addAll(declaredInstanceFields(object))
                set
        }
    }

}
