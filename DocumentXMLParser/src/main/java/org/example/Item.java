package org.example;

@JSONEntity
public class Item {
    @Documentable
    @JsonField()
    private String name = "Book";

    @Documentable(title = "sub_title")
    @JsonField(title = "sub_title")
    private String subTitle = "Science";

    @Documentable
    @JsonField(expectedType = JSONExpectedType.PLAIN)
    private double price = 12.0;
}
