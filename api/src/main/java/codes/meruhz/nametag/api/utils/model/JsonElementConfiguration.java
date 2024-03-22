package codes.meruhz.nametag.api.utils.model;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.concurrent.CompletableFuture;

public class JsonElementConfiguration extends AbstractConfiguration<JsonElement> {

    public JsonElementConfiguration(@NotNull File folder, @NotNull String name) {
        super(folder, name.endsWith(".json") ? name : name + ".json");
        super.configurationType = new JsonObject();
    }

    @Override
    public @NotNull CompletableFuture<Void> load() {
        @NotNull CompletableFuture<Void> completableFuture = new CompletableFuture<>();

        CompletableFuture.runAsync(() -> {
           try {

               if(super.getFile().length() == 0) {
                   super.save(new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create().toJson(super.getContent())).join();

               } else {
                   @NotNull JsonElement content = new JsonParser().parse(AbstractConfiguration.getFileContent(super.getFile()));
                   super.setContent(content);

                   if(!super.getContent().toString().equals(content.toString())) {
                       this.load().join();
                   }
               }

               completableFuture.complete(null);

           } catch (Exception e) {
               completableFuture.completeExceptionally(e);
           }
        });

        return completableFuture;
    }
}
