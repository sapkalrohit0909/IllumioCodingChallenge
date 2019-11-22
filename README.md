# IllumioCodingChallenge

# Instructions:
While creating an object of Firewall class .Pass the path of rules csv file.Those rules will get applied to the firewall.

# Implementation
I have used TreeMaps to store the rules so that when new input comes in we can check whether it is valid or not in constant time.I have used TreeMap as it stores the keys in sorted order.So my code will check the given input with the first rule then the second and goes on.So Basically it checks the first rule which is having more impact on most of the inputs.
In this code all corner condition.
for eg.
if range of IP is given. It is from 192.168.1.1 to 192.167.2.5 and the input has ip address of 192.168.1.154 then it should return true and This code works well in all corner conditions.
I did IP matching by converting it to 32 bit integer and then just checking new IP is in range or not.

# Teams
1.Data Team <br />
2.PlatForm Team <br />
3.Policy Team <br />
