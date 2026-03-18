# Crypto1010 Developer Guide

## Product Overview
Crypto1010 is a Java 17 command-line application that simulates blockchain wallet operations. It supports:
- wallet creation and listing
- key generation for wallets
- sending transactions with fee handling
- block viewing and chain validation
- JSON-based blockchain persistence

## Target User Profile
- Students learning blockchain fundamentals through a CLI workflow
- Users comfortable with terminal-based interaction
- Developers extending a compact Java codebase

## Value Proposition
The project provides a minimal but practical blockchain simulation where users can create transactions, inspect block internals, and validate chain integrity without external infrastructure.

## Architecture
The system follows a layered CLI architecture:
- `Duke`:
  - application entry point
  - reads user input loop
  - loads/saves blockchain through storage
  - delegates command parsing and execution
- `Parser`:
  - maps command words to `Command` subclasses
- `command` package:
  - encapsulates use cases (`create`, `send`, `validate`, etc.)
- `model` package:
  - blockchain and wallet domain objects
- `storage` package:
  - JSON serialization and deserialization for blockchain state

## Component Responsibilities

### `Duke`
File: `src/main/java/seedu/duke/Duke.java`
- Loads blockchain from `BlockchainStorage`
- Initializes `WalletManager`
- Runs the command loop
- Saves blockchain after each successful command and on `exit`

### `Parser`
File: `src/main/java/seedu/duke/Parser.java`
- Splits input into `commandWord` and arguments
- Returns the matching command instance via `CommandWord`
- Throws `IllegalArgumentException` for unknown commands

### Commands
Files: `src/main/java/seedu/duke/command/*`
- `CreateCommand`: creates wallets in-memory
- `ListCommand`: prints wallet names
- `KeygenCommand`: generates key pair for existing wallet
- `BalanceCommand`: computes wallet balance from blockchain transactions
- `SendCommand`: parses prefixed arguments, validates address/fee/speed, appends transfer + fee transactions
- `ValidateCommand`: validates entire chain
- `ViewBlockCommand`: displays block details by index
- `HelpCommand`: shows per-command help or full command list
- `ExitCommand`: marker command used by `Duke` to terminate after save

### Model
Files: `src/main/java/seedu/duke/model/*`
- `Block`:
  - immutable block data with SHA-256 hash computation
  - transaction data validation (non-empty entries)
- `Blockchain`:
  - ordered block list
  - chain validation (hash integrity + linkage + transaction sanity)
  - appending transaction blocks
  - balance calculation by parsing transaction strings
- `WalletManager` and `Wallet`:
  - in-memory wallet registry
  - per-wallet transaction history
  - optional key pair attachment
- `Key`:
  - RSA-like key pair generation primitives
  - derived integer wallet address for public keys
- `ValidationResult`:
  - immutable result object for chain validation

### Storage
File: `src/main/java/seedu/duke/storage/BlockchainStorage.java`
- Persists blockchain to `data/blockchain.json`
- Loads JSON, reconstructs block list, and validates chain
- Uses an internal lightweight JSON parser (no external JSON dependency)

## Key Implementation Details

### Transaction Format and Balance Computation
`Blockchain` parses transactions using:
`sender -> receiver : amount`

`getPreciseBalance(walletName)` scans all blocks and applies:
- subtraction when wallet is sender
- addition when wallet is receiver

### `SendCommand` Parsing Strategy
`SendCommand` uses prefix tokenization with:
- `w/`, `to/`, `amt/`, `speed/`, `fee/`, `note/`

Validation flow:
1. parse required prefixes
1. validate wallet existence
1. validate amount
1. validate recipient address format
1. resolve fee (manual or speed-based)
1. verify sufficient balance (`amount + fee`)
1. append transactions to blockchain

The command writes one transfer transaction and optionally one network-fee transaction to `network-fee`.

### Blockchain Validation
`validate()` checks:
1. each block hash matches recomputed hash
1. transaction data is non-empty and non-blank
1. each block links to previous block hash
1. genesis previous hash equals constant seed value

## Data Model Notes
- Blockchain is persistent.
- Wallets are currently session-only (not stored on disk).
- Because of this separation, persisted historical transactions may reference wallet names that are not currently present in `WalletManager`.

## Build and Test

### Build
```bash
./gradlew clean shadowJar
```
Output artifact:
- `build/libs/duke.jar`

### Run
```bash
./gradlew run
```

### Unit Tests
Run:
```bash
./gradlew test
```

Current test coverage includes:
- `BalanceCommand`
- `CreateCommand`
- `ListCommand`
- `SendCommand`
- `ValidateCommand`
- `ViewBlockCommand`
- parser basic test (`ParserTest`)

Not yet covered with dedicated tests:
- `HelpCommand`
- `ExitCommand`
- `KeygenCommand`
- `BlockchainStorage` and several model classes

## Non-Functional Requirements
- Language/runtime: Java 17
- Interface: command-line only
- Persistence: local JSON file in `data/`
- Deterministic validation behavior for loaded chains
- No external database dependency

## Manual Testing
1. Start with a clean run: remove or back up `data/blockchain.json`.
1. Run `./gradlew run`.
1. Create wallets:
   - `create alice`
   - `create bob`
1. List wallets:
   - `list`
1. Check balances from default chain:
   - `balance bob`
1. Send valid transfer:
   - `send w/bob to/0x1111111111111111111111111111111111111111 amt/1`
1. Validate chain:
   - `validate`
1. Inspect the new block:
   - `viewblock 2` (or latest index)
1. Exit:
   - `exit`
1. Restart and confirm blockchain persistence with `viewblock`.

## Known Limitations
- Wallets are not persisted.
- Recipient selection is address-based only; no wallet-name-to-address lookup command exists.
- `send` validates address format but does not verify on-chain ownership.
