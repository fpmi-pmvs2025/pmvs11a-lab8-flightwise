# Additional specification

## Constraints
- The application must be compatible with Android 15 or later.
- The application must use SQLite as the database for storing user and flights data.
- The application must be able to function offline for viewing previously stored data but require an internet connection for syncing data with external services or for real-time location services.

## Security
- All user data must be encrypted both at rest and in transit.
- Passwords must be stored securely.
- User's personal information must be protected and only accessible to user himself.
- Users must have control over their data, including the ability to view, modify, and delete their information.

## Reliability
- The application must be uptime and provide functionality of booking tickets to flights and allow to cancel them.
- The application must ensure the accuracy and consistency of the provided data.
- Automated backups must be performed regularly to prevent data loss, with the ability to restore data in case of corruption or accidental deletion.
- The application must handle errors gracefully, providing informative error messages and guidance on how to resolve issues.

## Performance Requirements
- The application must respond in several seconds on any action that includes queries or API fetches.
- The application architecture must be designed to support future scalability, allowing for the addition of new features and handling increased user loads.

## Usability Requirements
- The user interface must be intuitive and user-friendly.
- The application must support multiple languages, allowing users to choose their preferred language for the interface.
- The application must provide different themes for users' comfort.