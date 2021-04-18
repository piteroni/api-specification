# Api Specification

This command line tool lets you the input data can be validated for compliance with OpenAPI.

## Installation

### Docker container

A community Docker image is [publicly available on Docker hub](https://hub.docker.com/r/piteroni/api-specification).

`docker pull piteroni/api-specification`

Once pulled, the container can be run directly, but mount a volume containing the OpenAPI specification file so that it can be accessed.

`docker run --volume "$PWD":/resources piteroni/api-specification [options]`

## Usage

`$ docker run --rm -it -v "$PWD/resources":/resources piteroni/api-specification --oas /resources/oas.yml --data /resources/data.json`

## Definitions

### OAS

The validator supports API definition specifications - OpenAPI 3.0.

### Data

The Data to be validated, data must be in valid JSON format.

#### Schema

```json
{
    "type": "array",
    "items": {
        "$ref": "#/definitions/api_context"
    },
    "definitions": {
        "api_context" : {
            "type": "object",
            "required": [ "path", "method", "statusCode" ],
            "properties": {
                "path": {
                    "type": "string"
                },
                "method": {
                    "type": "string"
                },
                "statusCode": {
                    "type": "number"
                },
                "requestData": {
                    "type": "object",
                    "properties": {
                    }
                },
                "responseData": {
                    "type": "object",
                    "properties": {
                    }
                }
            }
        }
    }
}
```

#### Example

```json
[
    {
        "path": "/login",
        "method": "POST",
        "statusCode": 200,
        "requestData": {
            "email": "lind.lula@example.com",
            "password": "password"
        },
        "responseData": {
            "apiToken": "CDwK609KmQWqYpQokhMLjPFjhFRvN90Emh0Mm5Fz"
        }
    }
]
```
