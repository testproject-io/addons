# TestProject Addon - Slack Actions

This Addon provides actions to send messages to Slack incoming webhook.
Follow this tutorial to create a Slack incoming WebHook: https://api.slack.com/incoming-webhooks

## Actions

* Message Slack

### Fields

|  Field/Action   | Required | Input/Output | 
|-----------------|----------|--------------|
| webhook         |    X     |     INPUT    |
| text            |    X     |     INPUT    |
| color           |          |     INPUT    |
| title           |          |     INPUT    |
| title_link      |          |     INPUT    |
| author          |          |     INPUT    |
| additional_text |          |     INPUT    |

#### Input Fields

* `webhook` - Slack webhook URL (endpoint)

    > This is a mandatory parameter, otherwise action will fail.

    This parameter should contain only the URL of the Slack incoming webhook of the user.

* `text`

    The message you want to send to your Slack incoming webhook.

* `color` - String

    Color of the vertical line in the attachment payload. In Hexa-Decimal format. 

* `title`- String

    The title of the attachment.

* `title_link`- String

    Attach link to the title.

* `author` - String

    The author of the message.

* `additional_text` - String
    
    Any additional text you want to add to this message.

### Results

#### Failure Criteria

This action have several criterions that may result in failure assertion:

* When `webhook` or `message` inputs are empty.

* When the given `webhook` parameter is not a valid Slack incoming webhook.

* When server fails to respond.

* Connectivity errors

### Result Description

The action will report a message with the following information:

* In case the user didn't provide a webhook:
    > You must provide a webhook URL

* In case the user didn't provide a message:
    > Message can not be empty

* In case the user provided an invalid webhook URL (not in the correct format defined by Slack: "https://hooks.slack.com/services/{*}/{*}/{*}"):
    > Invalid slack webhook URL

* In case the server responded with a status code other than 200 (OK):
    > Server returned invalid response code: {{response_code}}

* In case of an exception that occurred when the request beeing sent:
    > throw new FailureException("Failed sending Slack text", e)

* In case of success:
    > The message was sent successfully

### Examples

Following TestProject platform conventions, unset parameters will be treated as nulls / empty strings and won't be used.

#### Example 1: POST Request with body

| Field            | Value                                                                          | Input/Output |
|------------------|--------------------------------------------------------------------------------|--------------|
| webhook          | https://hooks.slack.com/services/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  | INPUT        |
| message          | "This is a simple message"                                                     | INPUT        |
| color            | "#36a64f"                                                                      | INPUT        |
| title            | "My Title"                                                                     | INPUT        |
| title_link       | https://api.slack.com/                                                         | INPUT        |
| author           | "John Doe"                                                                     | INPUT        |
| additional_text  | "This is an additional text you can add to the attachment"                     | INPUT        |

Action result: *PASSED*

## Author

TestProject LTD. 2019

## License

Apache License 2.0

## Platform

Any