package net.sparklypower.sparklypaper.event.connection;

import io.papermc.paper.connection.PlayerLoginConnection;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Called when a player connection is about to be checked for Velocity forwarding.
 * <p>
 * This event can be used to bypass the Velocity forwarding check for internal special accounts.
 */
public class PlayerVelocityProxyCheckEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private final PlayerLoginConnection connection;
    private final UUID uniqueId;
    private final String name;
    private final boolean transferred;
    private boolean velocityForwardingRequired;

    @ApiStatus.Internal
    public PlayerVelocityProxyCheckEvent(@NotNull final PlayerLoginConnection connection, @NotNull final UUID uniqueId, @NotNull final String name, final boolean transferred) {
        super(true);
        this.connection = connection;
        this.uniqueId = uniqueId;
        this.name = name;
        this.transferred = transferred;
        this.velocityForwardingRequired = true;
    }

    /**
     * Gets the connection for this login attempt.
     * <p>
     * Note that the authenticated profile may not be available yet at this stage.
     *
     * @return the player login connection
     */
    @NotNull
    public PlayerLoginConnection getConnection() {
        return this.connection;
    }

    /**
     * Gets the unique ID requested by the client in the hello packet.
     *
     * @return the requested username
     */
    @NotNull
    public UUID getUniqueId() {
        return this.uniqueId;
    }

    /**
     * Gets the username requested by the client in the hello packet.
     *
     * @return the requested username
     */
    @NotNull
    public String getName() {
        return this.name;
    }

    /**
     * Gets whether this connection was transferred from another server via the transfer packet.
     *
     * @return true if this is a transferred connection
     */
    public boolean isTransferred() {
        return this.transferred;
    }

    /**
     * Gets whether Velocity forwarding is required for this connection.
     * <p>
     * By default, this returns {@code true} (Velocity forwarding is required).
     *
     * @return true if Velocity forwarding is required
     */
    public boolean isVelocityForwardingRequired() {
        return this.velocityForwardingRequired;
    }

    /**
     * Sets whether Velocity forwarding is required for this connection.
     * <p>
     * Setting this to {@code false} will allow the connection to proceed without
     * Velocity forwarding verification. The connection will be treated as a direct
     * connection with offline-mode UUID generation.
     * <p>
     * <b>Warning:</b> Only disable Velocity forwarding for connections you explicitly
     * trust. Disabling this check allows the client to connect directly, bypassing
     * the proxy's IP forwarding and authentication.
     *
     * @param required true to require Velocity forwarding, false to allow direct connection
     */
    public void setVelocityForwardingRequired(final boolean required) {
        this.velocityForwardingRequired = required;
    }

    @Override
    @NotNull
    public HandlerList getHandlers() {
        return handlers;
    }

    @NotNull
    public static HandlerList getHandlerList() {
        return handlers;
    }
}
