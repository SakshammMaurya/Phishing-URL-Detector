from urllib.parse import urlparse, urlencode
import ipaddress
import whois
import requests
import re
from datetime import datetime


#2 IP address in the URL
def Have_IP(url):
    try:
        hostname = urlparse(url).netloc
        # Remove port if present (e.g., 127.0.0.1:8080 → 127.0.0.1)
        hostname = hostname.split(':')[0]
        ipaddress.ip_address(hostname)
        return 1
    except:
        return 0


#3 "@" Symbol in the URL
def Have_At(url):
    if "@" in url:
        at = 1
    else: at = 0
    return at

#4 Length of URL
# If the length of URL >= 54 , the value assigned to this feature is 1 (phishing) or else 0 (legitimate).
def URL_Length(url):
    if len(url)<54:
        length=0
    else:
        length=1
    return length

#5 Depth of URL
# Gives number of '/' in URL (URL_Depth)

def URL_Depth(url):
    s = urlparse(url).path.split('/')
    depth = 0
    for i in range(len(s)):
        if len(s[i]) !=0:
            depth = depth+1
    return depth

#6 Redirection "//" in URL
#  We find that if the URL starts with “HTTP”, that means the “//” should appear in the sixth position
# However, if the URL employs “HTTPS” then the “//” should appear in seventh position.
def Redirection(url):
    pos = url.rfind('//')
    if pos > 6:
        if pos > 7:
            return 1
        else: return 0
    else: return 0

#7. "http/https" in Domain name
def https_Domain(url):
    domain = urlparse(url).netloc
    if 'https' in domain:
        return 1
    else: return 0


#8. Using URL Shortening Services “TinyURL”
#listing shortening services
shortening_services = r"bit\.ly|goo\.gl|shorte\.st|go2l\.ink|x\.co|ow\.ly|t\.co|tinyurl|tr\.im|is\.gd|cli\.gs|" \
                      r"yfrog\.com|migre\.me|ff\.im|tiny\.cc|url4\.eu|twit\.ac|su\.pr|twurl\.nl|snipurl\.com|" \
                      r"short\.to|BudURL\.com|ping\.fm|post\.ly|Just\.as|bkite\.com|snipr\.com|fic\.kr|loopt\.us|" \
                      r"doiop\.com|short\.ie|kl\.am|wp\.me|rubyurl\.com|om\.ly|to\.ly|bit\.do|t\.co|lnkd\.in|db\.tt|" \
                      r"qr\.ae|adf\.ly|goo\.gl|bitly\.com|cur\.lv|tinyurl\.com|ow\.ly|bit\.ly|ity\.im|q\.gs|is\.gd|" \
                      r"po\.st|bc\.vc|twitthis\.com|u\.to|j\.mp|buzurl\.com|cutt\.us|u\.bb|yourls\.org|x\.co|" \
                      r"prettylinkpro\.com|scrnch\.me|filoops\.info|vzturl\.com|qr\.net|1url\.com|tweez\.me|v\.gd|" \
                      r"tr\.im|link\.zip\.net"

def TinyURL(url):
    match=  re.search(shortening_services,url)
    if match:
        return 1
    else: return 0


#9. Prefix or Suffix "-" in Domain
#If the URL has '-' symbol in the domain part of the URL, the value assigned to this feature
#is 1 (phishing) or else 0 (legitimate).
def Prefix_Suffix(url):
    if '-' in urlparse(url).netloc:
        return 1
    else: return 0




def Web_Traffic(url):
    try:
        # Add scheme if missing
        if not url.startswith(('http://', 'https://')):
            url = 'http://' + url

        # Try connecting to the domain
        response = requests.get(url, timeout=5)

        # If HTTP status is OK (200–399), it's likely legitimate
        if response.status_code < 400:
            return 0
        else:
            return 1
    except requests.RequestException as e:
        print("Connection error:", e)
        return 1


def Domain_Age(domain_info):
    creation_date = domain_info.creation_date
    expiration_date = domain_info.expiration_date

    # Handle lists by picking the first date
    if isinstance(creation_date, list):
        creation_date = creation_date[0]
    if isinstance(expiration_date, list):
        expiration_date = expiration_date[0]

    if isinstance(creation_date, str):
        try:
            creation_date = datetime.strptime(creation_date, "%Y-%m-%d")
        except:
            return 1
    if isinstance(expiration_date, str):
        try:
            expiration_date = datetime.strptime(expiration_date, "%Y-%m-%d")
        except:
            return 1

    if creation_date is None or expiration_date is None:
        return 1
    age_in_days = abs((expiration_date - creation_date).days)

    return 1 if (age_in_days / 30) < 12 else 0


def Domain_End(domain_name):
    expiration_date = domain_name.expiration_date

    if isinstance(expiration_date, list):
        expiration_date = expiration_date[0]

    if expiration_date is None:
        return 1

    if isinstance(expiration_date, str):
        try:
            expiration_date = datetime.strptime(expiration_date, "%Y-%m-%d")
        except:
            return 1  # Invalid format, treat as suspicious

    #  SAFETY CHECK before subtraction
    if expiration_date is None:
        return 1

    try:
        exp_in_days = (expiration_date - datetime.now()).days
    except:
        return 1

    return 1 if (exp_in_days / 30) < 6 else 0


def iFrame(response):
    if not response or not hasattr(response, "text"):
        return 1

    if re.findall(r"<iframe", response.text, re.IGNORECASE):
        return 1
    else:
        return 0

def Mouse_Over(response):
        if not response or not hasattr(response, "text"):
            return 1  # Treat as suspicious if we can't check
        # Check for onmouseover JS trick (used in phishing to hide links)
        if re.findall(r"onmouseover\s*=\s*\"?window\.status", response.text, re.IGNORECASE):
            return 1
        return 0

def Right_Click(response):
        if not response or not hasattr(response, "text"):
            return 1  # Treat as suspicious if no response
        # Check for JS blocking right-click
        if re.findall(r"event\.button ?== ?2", response.text):
            return 0
        else:
            return 1

def Web_Forwards(response):
                if not response:
                    return 1
                if len(response.history) <= 2:
                    return 0
                else:
                    return 1


def extract_features(url):
    features = []

    # Address bar based features

    features.append(Have_IP(url))
    features.append(Have_At(url))
    features.append(URL_Length(url))
    features.append(URL_Depth(url))
    features.append(Redirection(url))
    features.append(https_Domain(url))
    features.append(TinyURL(url))
    features.append(Prefix_Suffix(url))

    # Domain based features
    dns = 0
    try:
        domain_name = whois.whois(urlparse(url).netloc)
    except:
        dns = 1

    features.append(dns)
    features.append(Web_Traffic(url))
    features.append(1 if dns == 1 else Domain_Age(domain_name))
    features.append(1 if dns == 1 else Domain_End(domain_name))

    # HTML & Javascript based features
    try:
        response = requests.get(url)
    except:
        response = None

    features.append(iFrame(response))
    features.append(Mouse_Over(response))
    features.append(Right_Click(response))
    features.append(Web_Forwards(response))

    return features
