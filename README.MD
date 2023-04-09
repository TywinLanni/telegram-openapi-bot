# Telegram openai bot

This is a telegram bot that uses the openai api to generate text.

## How to use

1. Create a telegram bot using the [BotFather](https://t.me/botfather)
2. Create a openai account and get your api key
3. Clone this repo
4. Create docker image
```bash
docker build -t openai-bot .
```

## Run

```bash
docker create --name bot -e CONTEXT='Представь что ты Владимир Жириновский.' -e GPT_MODEL_ID=gpt-3.5-turbo -e OPENAI_KEY=<your api key> -e TELEGRAM_BOT_TOKEN=<your bot token> -e WHITE_CHANNEL_LIST='-1001186042,-12834869,-60282,1541595' openai-bot
```

```bash
docker start bot
```

## Environment variables

- `CONTEXT` - system context for generation
- `GPT_MODEL_ID` - model id from openai
- `OPENAI_KEY` - openai api key
- `TELEGRAM_BOT_TOKEN` - telegram bot token
- `WHITE_CHANNEL_LIST` - list of channels where bot will work
- `RANDOM_MESSAGE_CHANCE` - chance to send random message (1 - MAX_INT) default 0.1 