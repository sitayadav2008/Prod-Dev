#!/bin/bash

NUM_COMMITS=10
touch Main.java

START_DATE=$(date -d "2025-06-15" +%s)
END_DATE=$(date -d "2025-10-01" +%s)

declare -A used_dates

# Array of Java developer style commit messages
messages=(
"Refactor UserService for readability"
"Fix NullPointerException in OrderController"
"Add unit tests for PaymentService"
"Optimize database query in ProductRepository"
"Implement JWT authentication"
"Update README with project setup"
"Improve exception handling in BillingService"
"Add logging for debugging purposes"
"Refactor code for better modularity"
"Fix bug in user registration flow"
)

for i in $(seq 0 $(($NUM_COMMITS-1)))
do
  while true; do
    RANDOM_TS=$((START_DATE + RANDOM % (END_DATE - START_DATE + 1)))
    RANDOM_DAY=$(date -d @"$RANDOM_TS" +%Y-%m-%d)
    if [[ -z "${used_dates[$RANDOM_DAY]}" ]]; then
      used_dates[$RANDOM_DAY]=1
      break
    fi
  done

  COMMIT_DATE=$(date -d "$RANDOM_DAY" +"%Y-%m-%dT%H:%M:%S")

  # Random change in the file
  echo "// random change $RANDOM" >> Main.java

  # Commit with realistic Java dev message
  git add Main.java
  GIT_COMMITTER_DATE="$COMMIT_DATE" git commit --date="$COMMIT_DATE" -m "${messages[$i]}"
done
// random change 28322
// random change 10889
// random change 28249
// random change 9778
// random change 2840
// random change 15687
// random change 9041
// random change 28815
// random change 7283
// random change 5253
// random change 13821
// random change 20773
// random change 11632
// random change 7525
// random change 24162
// random change 6299
// random change 16666
// random change 1833
// random change 24067
// random change 11122
// random change 11545
// random change 27469
// random change 24856
// random change 12198
// random change 15123
// random change 20457
// random change 30792
// random change 10752
// random change 22199
// random change 26124
// random change 17015
// random change 18207
// random change 24244
// random change 9565
// random change 12378
// random change 27789
