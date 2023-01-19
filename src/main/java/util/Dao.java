package util;

public interface Dao<T> {

    public void create(T object);

    public void update(long id, T object);

    public void delete(long id);

    public boolean isValid(long id);

}
