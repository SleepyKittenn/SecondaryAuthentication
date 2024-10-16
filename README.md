# SecondaryAuthentication-Bukkit

![Java](https://img.shields.io/badge/Java-17-blue) 
![Version](https://img.shields.io/badge/version-1.0.0-yellow.svg)
![API-Version](https://img.shields.io/badge/api--version-1.13-lightgrey.svg)

**SecondaryAuthentication-Bukkit** provides an additional layer of security for your server by requiring specified players to verify their identity upon joining using a secret code. This is particularly useful for server admins and staff to ensure extra protection.

## Features

- **Player Verification:** Requires specified players to verify using a secret code.
- **Kick Unverified Players:** Automatically kicks players who fail to verify within a specified timeout.
- **Configurable Commands:** Commands to manage verification and plugin configuration.
- **Customizable Settings:** Easily configure which players need verification and the timeout duration.
- **Authentication Session:** Users are prompted to verify everytime the session resets.

## Installation

1. **Download** the latest version of the plugin from the [releases](https://github.com/SleepyKittenn/SecondaryAuthentication/releases) page.
2. **Place** the `SecondaryAuthentication-Bukkit.jar` file into your server's `plugins` directory.
3. **Start** the server to generate the default configuration files.
4. **Edit** the `config.yml` file in the `plugins/SecondaryAuthentication-Bukkit` directory to customize your settings.
5. **Reload** the server or use `/secondaryauthentication reload` to apply the configuration changes.

## Configuration

The main configuration file `config.yml` allows you to specify the players who need to verify and their secret codes. Additionally, you can configure global settings like the verification timeout:

```yaml
required_players:
  PlayerName1:
    code: "secretCode1"
    verificationDuration: 300  # The duration in seconds for which the verification will be valid (300 seconds = 5 minutes).
  PlayerName2:
    code: "secretCode2"
    verificationDuration: -1  # Verification not required again during the session.

global_settings:
  verificationTimeout: 60  # The duration in seconds before any player is kicked for not verifying.
```

## Commands

- **/verify <secret code>**: Verify yourself with a secret code.

- **/secondaryauthentication reload**: Reload the SecondaryAuthentication plugin configuration.
  - **Permission**: `secondaryauthentication.reload`

## Permissions

- **secondaryauthentication.reload**: Allows the user to reload the SecondaryAuthentication plugin configuration.
  - **Default**: `op`

## Development

### Building From Source

1. **Clone** the repository:
    ```sh
    git clone https://github.com/your-username/SecondaryAuthentication-Bukkit.git
    cd SecondaryAuthentication-Bukkit
    ```

2. **Build** the plugin using Maven:
    ```sh
    mvn clean package
    ```

3. **Find** the compiled JAR in the `target` directory.

## Contributing

Contributions are welcome! Feel free to submit a pull request or open an issue to address bugs, suggest new features, or improve documentation.

## Credits

Developed by **sleepy**.

## Support

For any questions or issues, please open an issue on the [GitHub repository](https://github.com/SleepyKittenn/SecondaryAuthentication/issues).

---

Thank you for using SecondaryAuthentication-Bukkit! 🎉
