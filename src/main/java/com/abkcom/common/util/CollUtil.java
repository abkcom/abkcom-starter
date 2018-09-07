package com.abkcom.common.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CollUtil<T>
{
  /**
   * Splits up the collection into sublists containing the specified number of elements in it. This
   * allows to convert a very large collection into a list of smaller lists of the specified size.
   *
   * @param c collection to be broken down into sublists.
   * @param size specifies max number of elements in the resulting sublists. If size is equal or
   *          less than 0, the same collection is returned.
   * @return a list of sublists.
   */
  public static <T> List<List<T>> split(Collection<T> c, int size)
  {
    if (c == null)
    {
      return null;
    }

    LinkedList<List<T>> result = new LinkedList<List<T>>();
    ArrayList<T> all = new ArrayList<T>(c);

    if (size <= 0)
    {
      result.add(all);
      return result;
    }
    if (c.size() == 0)
    {
      return result;
    }

    int idx = 0;
    for (; idx + size < c.size(); idx += size)
    {
      result.add(all.subList(idx, idx + size));
    }
    result.add(all.subList(idx, all.size()));
    return result;
  }

  /**
   * Returns a new array that is a subsection (slice) of source array
   *
   * @param source the array from which you want a subsection.
   * @param offset defines the number of elements to the right of position zero and where coping
   *          should start to take place.
   * @param length defines the number of elements that should be copied.
   * @return an array containing elements from source[offset] to source[offset + length - 1]
   */
  public static int[] slice(int[] source, int offset, int length)
  {
    int[] slice = new int[length];
    System.arraycopy(source, offset, slice, 0, length);

    return slice;
  }

  /**
   * Returns a new array that is a subsection (slice) of source array
   *
   * @param source the array from which you want a subsection.
   * @param offset defines position of the element in the source array which will become the first
   *          element of the resulting slice. In other words, it points to the element where the
   *          slice begins.
   * @param length defines the number of elements to be included into the resulting slice.
   * @return an array containing elements from source[offset] to source[offset + length - 1]
   */
  public static Object[] slice(Object[] source, int offset, int length)
  {
    Object[] slice = (Object[]) java.lang.reflect.Array.newInstance(source
        .getClass().getComponentType(), length);
    System.arraycopy(source, offset, slice, 0, length);

    return slice;
  }

  /**
   * Returns a set containing only the specified object.
   *
   * @param o an object to be presented as a <code>java.util.Set</code> collection.
   */
  public static <T> Set<T> toSet(T... o)
  {
    Set<T> s = new LinkedHashSet<T>(o.length);
    for (T el : o)
    {
      s.add(el);
    }
    return s;
  }

  /**
   *
   * @param <T>
   * @param clazz
   * @param c
   * @return
   */
  public static <T> List<T> castList(Class<? extends T> clazz, Collection<?> c)
  {
    List<T> r = new ArrayList<T>(c.size());
    for (Object o : c)
    {
      r.add(clazz.cast(o));
    }
    return r;
  }

  public static <T> List<T> iteratorToList(Iterator<T> it)
  {
    if (it == null)
    {
      return null;
    }
    List<T> list = new ArrayList<T>();
    while (it.hasNext())
    {
      list.add(it.next());
    }
    return list;
  }

  /**
   * null safe
   *
   * @param c
   * @return true if c is null or empty
   */
  public static boolean isEmpty(Collection<?> c)
  {
    return c == null || c.isEmpty();
  }

  public static boolean notEmpty(Collection<?> c)
  {
    return !isEmpty(c);
  }

  /**
   * This method wont throw IndexOutOfBoundsException
   *
   * @param list
   * @param index
   * @return
   */
  public static <T> T safeGet(List<T> list, int index)
  {
    try
    {
      return list.get(index);
    }
    catch (Exception e)
    {}
    return null;
  }

  public static <T> void safeAdd(List<T> list, T obj)
  {
    if (obj != null)
    {
      list.add(obj);
    }
  }

  public static <K, V> Map<K, V> toMap(K key, V value)
  {
    Map<K, V> m = new HashMap<K, V>();
    m.put(key, value);
    return m;
  }

  public static <T> T[] toArray(T... array)
  {
    return array;
  }

  public static <T> List<T> toList(T... array)
  {
    return new ArrayList<T>(Arrays.asList(array));
  }

  public static <T> List<T> limit(Collection<T> coll, int max)
  {
    ArrayList<T> limited = new ArrayList<T>(coll);
    if (coll.size() <= max)
    {
      return limited;
    }
    return limited.subList(0, max);
  }

  // public static <T> List<T> toList(T... array)
  // {
  // List<T> list = new ArrayList<T>();
  // for (T item : array)
  // {
  // list.add(item);
  // }
  // return list;
  // }
}
