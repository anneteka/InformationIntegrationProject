package main.util.cvs.model;

import main.database.entity.source.EBook;

public abstract class CsvBook {
    public abstract EBook toEBook();
}
