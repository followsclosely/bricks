package io.github.followsclosley.brick.web.converter;

/**
 * A converter that maps objects from one class to another.
 * Converters are typically used to convert objects between different representations, such as JSON and Java objects.
 * To use a converter, simply call the `map()` method with the object to be converted and the class to convert it to.
 * For example, to convert an object of type `A` to an object of type `B`, you would call the following method:
 *
 * ```
 * P map(Object bean, Class<P> klass)
 * ```
 * The converter will then return a new object of type `B` that represents the converted object.
 *
 * @param <P> the type of the object to be returned
 */
public interface Converter {

    /**
     * Maps an object from one class to another.
     *
     * @param bean the object to be converted
     * @param klass the class to convert the object to
     * @return the converted object
     */
    <P> P map(Object bean, Class<P> klass);
}
