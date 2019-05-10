
User black list
========================

Hi developer
Our bidder service has been running in production for a long time. based on a const bid logic. meaning, each request got the same, const bid. 
bid was calculated before hand, and only delivery of the value was required.

Through time, we've noticed that certain users are non-profitable for us. We've collected and placed them in a DB (redis)
The logic we're looking for is this: when a request is received - check if the user exists  in the dataset.
If it's found - do not bid on this user. If not - return the usual const bid.

We've implemented the code and deployed it to production. 
While the logic is what we'd expect and bids are blocked properly, we're seeing a big drop in our servers throughput (number of messages/sec handled)

Please help us out in getting our systems throughput back to normal

Some additional info:
* While User numbers are large, A user will repeat in a short period of time (the session)
* Bidder actors are a limited pool (we currently user 8 bidders in our system on an 8 core machine)
* User data is stored in redis


Feel free to make any changes in current code, while keeping it's external APIs & expected behaviour.
If you find some issues with current impl, please state them, and how you would correct them.

Please state any assumption you make, and feel free to ask for any clarifications.

Thanks & Good luck!




