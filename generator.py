import json
import random
from datetime import datetime, timedelta

# Paths to input/output files
input_path = "C:\\Users\\Vaibhav\\Documents\\work\\ApproveMate\\src\\main\\resources\\static\\updated_java_libraries.json"
output_path = "C:\\Users\\Vaibhav\\Documents\\work\\ApproveMate\\src\\main\\resources\\static\\licensing_data_synced_with_libraries.json"

# Sample values
software_names = [
    "jackson-datatype-jsr310", "gson", "spring-core", "httpclient", "log4j",
    "hibernate-core", "mockito-core", "slf4j-api", "guava", "commons-lang3"
]
versions = ["2.10.5", "2.9.3", "3.1.0", "1.14.5", "3.12.2"]
initial_use_cases = ["Internal", "Distributed"]
statuses = ["Approved", "Unapproved"]

# Function to generate a future date
def generate_random_date():
    return (datetime(2025, 1, 1) + timedelta(days=random.randint(0, 1000))).strftime("%d-%b-%y").upper()

# Load input JSON
with open(input_path, "r") as f:
    library_data = json.load(f)

# Extract unique ltIDs
unique_ltids = list(set(lib["lic_tech_id"] for lib in library_data))

# Track used (baID, scope, software) combinations
used_combinations = set()
output_records = []

# Generate licensing data
for lt_id in unique_ltids:
    used_scopes_for_lt_id = set()
    for _ in range(random.randint(2, 4)):
        ba_id = random.randint(196300, 196600)
        
        # Generate a unique scope per ltID
        scope = None
        for attempt in range(10):  # Limit attempts to avoid infinite loop
            candidate_scope = f"Scope-{random.randint(1000, 9999)}"
            if candidate_scope not in used_scopes_for_lt_id:
                scope = candidate_scope
                used_scopes_for_lt_id.add(scope)
                break
        if scope is None:
            # Could not find a unique scope after 10 tries, skip this iteration
            continue
        
        for _ in range(random.randint(1, 3)):
            sw_name = random.choice(software_names)
            version = random.choice(versions)
            combo_key = (ba_id, scope, sw_name)
            if combo_key in used_combinations:
                continue
            used_combinations.add(combo_key)
            status = random.choice(statuses)
            record = {
                "ltID": lt_id,
                "Licensor": "Licensed Dataset",
                "Software Name": sw_name,
                "Version": version,
                "baID": str(ba_id),
                "Products (Scope of BA)": scope,
                "requestingProduct": f"Product-{random.randint(100,999)}",
                "status": status,
                "licensetype": " ",
                "ltExpiryDate": "Not Set",
                "ltSuperseededBy": "Not Set",
                "baExpiryDate": generate_random_date() if status == "Approved" else "Not Set",
                "initialUseCase": random.choice(initial_use_cases),
                "ltExpiryStatus": "Not Set",
                "baExpiryStatus": "Not expired" if status == "Approved" else "Not Set",
                "plslturl": "xxxx",
                "plsbaurl": "xxxx",
                "agreementtype": "Open Source",
                "sourceURL": "Not Set",
                "componentURL": "https://disposable.github.io/disposable-email-domains/domains.txt"
            }
            output_records.append(record)


# Save output to JSON
with open(output_path, "w") as f:
    json.dump(output_records, f, indent=4)

print(f"✅ Licensing data generated: {output_path}")
