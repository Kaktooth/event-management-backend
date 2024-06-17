## Backend for managing events

- ### Spring boot 3.0.0
- Java version 21 and compatible with lower versions

### Run

Build with Gradle.

---

```bash
./gradlew build
```

Run Docker container.

---

```bash
docker-compose.exe -f ./compose.yaml -p manage_events_backend up -d
```

### Functionality

#### Initial data

User created events and chats.

| User (Creator) | Created event             | Created chat |
|----------------|---------------------------|--------------|
| User           | Public Event              |              |
| User2          | Friends Event             |              |
| User3          | Private Event, Chat Event | New Chat     |
| Moderator      |                           |              |

For every user password is `123123`. Basic Authentication is used for every endpoint.

Moderator can approve event to show on website. Moderator have own url endpoint that he can use.
Endpoint `/moderate-events` returns all not approved events that he can approve with
endpoint `/event/{event-id}/approve`.

---

| Event         | Visibility                                | Shows to users |
|---------------|-------------------------------------------|----------------|
| Public Event  | Public (shows to every one if approved)   | Everyone       |
| Friends Event | Visible to creator friends                | User           |
| Private Event | Visible to selected individuals           | User, User3    |
| Chat Event    | Visible to selected individuals from chat | User2          |

    Note. User and User2 are friends and its visible only to User when using /events endpoint. 
    Creators can see their created event with /user-events/{user-id-that-created-event} url endpoint.
    User2 and User3 are guests in the "New Chat" but only User2 was selected from that chat.

All approved events is returned by `http://localhost:8080/events` endpoint and specific events can be updated or deleted by its creator and
can be saved to database.
Url to save event is `http://localhost:8080/save-event`. Default approved property is false when event is saved.
Body example:

```json
{
  "name": "Chat Event 2",
  "visibility": "SELECTED_CHAT_INDIVIDUALS",
  "guests": [
    {
      "id": "8a9f7c21-e436-42c1-9386-1241cf7bcfb9",
      "username": "User2",
      "authority": "USER"
    }
  ],
  "chat": {
    "id": "3fb04bbf-8109-4244-b550-44d1668e758f"
  }
}
```

Url to update event is `http://localhost:8080/event/7249a361-11ea-46f8-b46b-518e943b2530/update`. If event is updated,
approved value is false.
Body example for updating name of "Chat Event":
```json
{
    "id": "7249a361-11ea-46f8-b46b-518e943b2530",
    "name": "Chat Event 2x",
    "visibility": "SELECTED_CHAT_INDIVIDUALS",
    "guests": [
        {
            "id": "8a9f7c21-e436-42c1-9386-1241cf7bcfb9"
        }
    ],
    "chat": {
        "id": "3fb04bbf-8109-4244-b550-44d1668e758f"
    }
}
```
Url to delete "Friends Event" is `http://localhost:8080/event/ab3c6b44-cfd2-433d-b202-4069c2dd7e02/delete`