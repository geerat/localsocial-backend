# localsocial_backend
LocalSocial is a basic Twitter clone. 

# TODO
## Security
- AWS Cognito
- Spring Security
- Save access tokens in user cookies rather than username
- require access token for all api endpoints

## Add Follow Functionality
- Create schema for followers

## Like Functionality
- Hover on like count to see who liked a post 

## MISC
- Assign specific avatar or color to user
- Create endpoint for fetching posts for certain users follows
- Implement websockets to push updates to user
- Better error handling

## Known Bugs
- If username contains a comma that user will count as multiple likes (and it can like unlimited times