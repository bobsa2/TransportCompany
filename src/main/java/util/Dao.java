package util;

public interface Dao<T> {

    public void create(T object);

    public void update(int id, T object);

    public void delete(int id);

}
