package codes.meruhz.nametag.api.utils;

import com.google.gson.*;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import org.jetbrains.annotations.NotNull;

/**
 * This utility class, ComponentUtils, provides methods for working with BaseComponents in a Minecraft plugin.
 */
public final class ComponentUtils {

    private ComponentUtils() {
    }

    /**
     * Serialize a single BaseComponent into a JSON string.
     *
     * @param component The BaseComponent to serialize.
     * @return A JSON string representing the serialized BaseComponent.
     */
    public static @NotNull String serialize(@NotNull BaseComponent component) {
        if(component instanceof TextComponent) {
            @NotNull TextComponent text = (TextComponent) component;

            if(!text.hasFormatting() && (text.getExtra() == null || text.getExtra().isEmpty())) {
                @NotNull JsonObject object = new JsonObject();
                object.addProperty("text", text.getText());

                return object.toString();
            }
        }

        return ComponentSerializer.toString(component);
    }

    /**
     * Serialize an array of BaseComponents into a JSON string.
     *
     * @param components The array of BaseComponents to serialize.
     * @return A JSON string representing the serialized array of BaseComponents.
     * @throws JsonParseException if the input array is empty.
     */
    public static @NotNull String serialize(BaseComponent... components) {
        if(components.length == 0) {
            throw new JsonParseException("Empty array of base components");

        } else if(components.length == 1) {
            return ComponentUtils.serialize(components[0]);

        } else {
            @NotNull JsonArray array = new JsonArray();
            @NotNull JsonParser parser = new JsonParser();

            for(BaseComponent component : components) {
                @NotNull String serialized = ComponentUtils.serialize(component);

                try {
                    array.add(parser.parse(serialized));

                } catch (JsonSyntaxException var8) {
                    array.add(new JsonPrimitive(serialized));
                }
            }

            return array.toString();
        }
    }

    /**
     * Extract the text content from an array of BaseComponents and return it as a single string.
     *
     * @param components The array of BaseComponents to extract text from.
     * @return A string containing the concatenated text from the BaseComponents.
     */
    public static @NotNull String getText(@NotNull BaseComponent... components) {
        @NotNull StringBuilder str = new StringBuilder();

        for (BaseComponent component : components) {
            str.append(component.toLegacyText());
        }

        return str.toString().startsWith("§f") ? str.toString().replaceFirst("§f", "") : str.toString();
    }
}
