/**
 * A horizontal collection of cells.
 *
 * Responsible for telling cells about their borders.
 *
 * Responsible for knowing how wide it will be, can be, should be?
 */
class Row {

    String externalBorder = ""
    String internalBorder = ""
    public enum LayoutStyle {
        EQUIDISTANT_BORDERS //each column takes the same amount of space
    }

    List<Cell> cells = []
    Integer desiredWidth
    LayoutStyle layout = LayoutStyle.EQUIDISTANT_BORDERS

    void setContent(List<String> rawContent) {
        cells = []
        rawContent.each {
            cells << new Cell(content: it)
        }
    }

    String getFormattedContent() {
        equidistantLayout()
    }

    String equidistantLayout() {
        Integer cellSize = desiredWidth / cells.size()
        Integer spill = desiredWidth - (cellSize * cells.size())

        String result = ""
        0.upto(cells.size() - 1) {
            Cell cell = cells[it]
            cell.borderLeft = internalBorder
            cell.borderRight = ""
            if (it == 0) {
                cell.borderLeft = externalBorder
            }
            if (it == cells.size() - 1) {
                cell.borderRight = externalBorder
            }
        }
        cells.each{ cell->
            cell.desiredWidth = cellSize
            if (spill > 0) {
                cellSize++
                spill--
            }
            result += cell.formattedContent
        }

        return result
    }
}
