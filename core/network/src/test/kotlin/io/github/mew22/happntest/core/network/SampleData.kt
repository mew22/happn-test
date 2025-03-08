package io.github.mew22.happntest.core.network

val sampleResponseBody = """
    [
        {
            "id": 1,
            "title": "First task",
            "completed": false
        },
        {
            "id": 2,
            "title": "Second task",
            "completed": true
        }
    ]
""".trimIndent()

internal val sampleResponse = listOf(
    JsonTestTodo(id = 1, title = "First task", completed = false),
    JsonTestTodo(id = 2, title = "Second task", completed = true),
)
