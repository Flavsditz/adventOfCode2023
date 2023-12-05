fun getResourceAsLines(path: String): List<String> =
    object {}.javaClass.getResourceAsStream(path)?.bufferedReader()!!.readLines()