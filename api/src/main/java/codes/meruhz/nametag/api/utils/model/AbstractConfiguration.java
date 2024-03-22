package codes.meruhz.nametag.api.utils.model;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Blocking;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * The abstract base class for handling configurations in a Java application.
 *
 * @param <T> The type of configuration content.
 */
public abstract class AbstractConfiguration<T> {

    // Folder and file properties
    private final @NotNull File folder, file;
    private final @NotNull String name;

    // Content of the configuration
    protected @Nullable T configurationType;

    /**
     * Constructor to initialize the configuration with the specified folder and name.
     *
     * @param folder The directory where configuration files are stored.
     * @param name   The name of the configuration.
     * @throws RuntimeException If the folder cannot be created.
     */
    protected AbstractConfiguration(@NotNull File folder, @NotNull String name) {
        // Ensure the folder exists or create it
        if (!folder.isDirectory() && !folder.mkdirs()) {
            throw new RuntimeException("Folder " + folder.getAbsolutePath() + " could not be created");
        }

        // Initialize properties
        this.folder = folder;
        this.name = name.replace(" ", "_");
        this.file = new File(this.getFolder(), this.getName());

        // Create the configuration file asynchronously
        this.createFile().join();
    }

    /**
     * Gets the folder associated with the configuration.
     *
     * @return The configuration folder.
     */
    public final @NotNull File getFolder() {
        return this.folder;
    }

    /**
     * Gets the file representing the configuration.
     *
     * @return The configuration file.
     */
    public final @NotNull File getFile() {
        return this.file;
    }

    /**
     * Gets the name of the configuration.
     *
     * @return The configuration name.
     */
    public final @NotNull String getName() {
        return this.name;
    }

    /**
     * Gets the content of the configuration.
     *
     * @return The configuration content.
     * @throws NullPointerException If the configuration is not initialized.
     */
    public @NotNull T getContent() {
        return Optional.ofNullable(this.configurationType).orElseThrow(() -> new NullPointerException("Configuration '" + this.getName() + "' is not initialized"));
    }

    /**
     * Sets the content of the configuration and optionally loads it.
     *
     * @param content   The configuration content to set.
     * @param autoLoad  Flag indicating whether to automatically load the configuration after setting the content.
     */
    public void setContent(@NotNull T content, boolean autoLoad) {
        this.configurationType = content;

        // Load the configuration if autoLoad is true
        if (autoLoad) this.load().join();
    }

    /**
     * Sets the content of the configuration without automatically loading it.
     *
     * @param content The configuration content to set.
     */
    public void setContent(@NotNull T content) {
        this.setContent(content, false);
    }

    /**
     * Asynchronously creates the configuration file.
     *
     * @return A CompletableFuture representing the completion of the file creation.
     */
    @Blocking
    public @NotNull CompletableFuture<Void> createFile() {
        @NotNull CompletableFuture<Void> completableFuture = new CompletableFuture<>();

        // Run file creation logic asynchronously
        CompletableFuture.runAsync(() -> {
            try {
                // Create parent directory if it doesn't exist
                @NotNull File parent = this.getFile().getParentFile();
                if (!parent.exists() && !parent.mkdirs()) {
                    throw new IOException("Failed to create parent directory for configuration '" + this.getName() + "'");
                }

                // Create the configuration file
                //noinspection ResultOfMethodCallIgnored
                this.getFile().createNewFile();

                completableFuture.complete(null);

            } catch (Exception e) {
                completableFuture.completeExceptionally(e);
            }
        });

        return completableFuture;
    }

    /**
     * Asynchronously saves the configuration to the file in the specified format.
     *
     * @param format The format in which to save the configuration.
     * @return A CompletableFuture representing the completion of the save operation.
     */
    @Blocking
    public @NotNull CompletableFuture<Void> save(@NotNull String format) {
        @NotNull CompletableFuture<Void> completableFuture = new CompletableFuture<>();

        // Run save logic asynchronously
        CompletableFuture.runAsync(() -> {
            try (FileOutputStream fileOutputStream = new FileOutputStream(this.getFile())) {
                @NotNull BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8));

                // Write the configuration in the specified format
                synchronized (this) {
                    bufferedWriter.write(format);
                }

                bufferedWriter.close();
                completableFuture.complete(null);

            } catch (Exception e) {
                completableFuture.completeExceptionally(e);
            }
        });

        return completableFuture;
    }

    /**
     * Loads the configuration asynchronously. This method must be implemented by subclasses.
     *
     * @return A CompletableFuture representing the completion of the load operation.
     */
    @ApiStatus.OverrideOnly
    public abstract @NotNull CompletableFuture<Void> load();

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        AbstractConfiguration<?> that = (AbstractConfiguration<?>) o;
        return Objects.equals(folder, that.folder) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(folder, name);
    }

    /**
     * Gets an InputStream for a file from the classpath resources.
     *
     * @param fileName The name of the file in the resources.
     * @return The InputStream for the specified resources file.
     * @throws NullPointerException If the resources file cannot be found.
     */
    public static @NotNull InputStream getFromResources(@NotNull String fileName) {
        fileName = "/" + fileName;

        // Get InputStream for the resources file
        @Nullable InputStream inputStream = AbstractConfiguration.class.getResourceAsStream(fileName);

        // Throw exception if the resources file is not found
        if (inputStream == null) {
            throw new NullPointerException("Could not find resources file '" + fileName + "'");
        }

        return inputStream;
    }

    /**
     * Reads the content of a file into a string.
     *
     * @param file The file to read.
     * @return The content of the file as a string.
     * @throws IOException If an I/O error occurs while reading the file.
     */
    public static @NotNull String getFileContent(@NotNull File file) throws IOException {
        @NotNull FileInputStream fileInputStream = new FileInputStream(file);
        @NotNull BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream, StandardCharsets.UTF_8));
        @NotNull StringBuilder content = new StringBuilder();
        @NotNull String line;

        // Read the file content line by line
        while ((line = bufferedReader.readLine()) != null) {
            content.append(line).append("\n");
        }

        bufferedReader.close();
        return content.toString();
    }
}