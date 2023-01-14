package util;

public interface Dao<T> {

    public <T> void create(T object);

    public <T> void update(T object);

    public <T> void delete(T object);

}
