# HealthCare Billing and Revenue management
## PRODUCT VISION

We have already created "Scrum Team - Billing and Revenue Management" group in Canvas.

**[Access Git Repository from here](https://github.com/rohit-njit/HealthCare---Billing-and-Revenue-management/)**

**Team Name:** SudoPower

|Team Members         |Role                                    |
|---------------------|----------------------------------------|
|Rohit Sonawane       |Data Architect                          |
|Ankit Darekar        |UI/UX Developer                         |
|Nitesh Lalan Jha     |Product management / Business Analyst   |
|Priyam A Sahoo       |Quality & DevOps                        |
|Chandresh Sikarwar   |Microservices Developer                 |

**Roster:** Sprint call on weekdays from 3-4PM to track the daily progress/agile methodology.

**Product Vision:** Making Billing and revenue management as a service which is simple, secure, convenient and extensible for any Health Care Product.

**Near Vision:** Designing architecture for the billing module and understanding the dependencies with other modules of the product.

**Stakeholders:** BackOffice, Patinets, Insurance Company.

**User Persona:**

**1. BackOffice:** Any user responsible for bill generation and bill tracking and they should be able to generate and send bills
to payee and keep the historical records.

**2. Patients:** Anyone who visit a doctor's office with the intention to get treatment for his/her ailment and recieve's 
billing information based on their encounters; Patient can also see information/details about their prior visits.

**3. Insurance Company:** Insurer responsible for paying off patient's bill payment. Insurance company should recieve billing 
information based on Insurance taken by the patient.

**[Access Backlogs on Pivotal Tracker](https://www.pivotaltracker.com/n/projects/2532665)**

**Current Product backlog and rationale**
1. As an administrator, I should be able to add, modify, configure rates based on CPT, HPPCS, ICD codes.
2. As an administrator, I should be able to view the rates based on CPT, HPPCS, ICD codes.
3. As a biller, I should be able to get insurance details - third party.
4. As a biller, I should be able to validate the superbill - ensure that the procedures coded are billable.
5. As a biller, I should be able to generate a claim based on superbill, patient info, rates and provider.
6. As a biller, I should be able to ensure the standards of compliance, both for coding and format.
7. As a biller, I should be able to send claims to clearinghouse and/or insurer.
9. As a biller, I should be able to receive claims back from clearinghouse and/or insurer.
10. As a biller, I should be able to send left over amount to second or more insurer.
11. As a biller, I should be able to correct rejected bill and resend it.
12. As a biller, I should be able to process denied claim.
13. As a biller, I should be able to to send an appeal in case of a discrepancies.
14. As a biller, I should be able to make statement to the patient.
15. As a biller, I should be able to follow-up, send reminder to the patient.

The logical order for Billing and Revenue module would be to configure rates, receiving the superbills, generating claims, communication with insurer and clearinghouse, adjusting claims, generating and sending statements to the patients. **[More info](https://www.pivotaltracker.com/n/projects/2532665)**

**Ready State**

|Title|User story opening sentence|Additional details|Definition of Ready|Story points|Estimation (Hrs per week) |
|-----|---------------------------|------------------|-------------------|------------|--------------------------|
|Add, modify, configure rates |As an administrator, I should be able to add, modify, configure rates based on CPT, HPPCS, ICD codes. |Administrator should be able to configure the system with the initial rates applied to different CPT, HPPS, ICD. Administrator should also be able to modify add these configuration. This will make billing module extensible to any Health Care Provider.|1. There should be services to add, modify the rates. 2. There should a database with the rates of different CPT, HPPS, ICD.| 3 |20|
|View the rates based on CPT, HPPCS, ICD codes |As an administrator, I should be able to view the rates based on CPT, HPPCS, ICD codes. |Administrator should be able to view the different rates of CPT, HPPCS, ICD.|A UI to view the rates.Possibly, a UI to manually add rates to a bill. (May not be deliverable) | 3|20|
|Get insurance details|As a biller, I should be able to get insurance details - third party |Claims also have information about the provider, listed via a National Provider Index (NPI) number. Some claims will also include a Place of Service code, which details what type of facility the medical services were performed in. |A UI or service to gather information about the insurer listed via a National Provider Index (NPI) number.|3|20|
|Validate the superbill|As a biller, I should be able to validate the superbill - ensure that the procedures coded are billable|The biller reviews the codes to ensure that the procedures coded are billable. Whether a procedure is billable depends on the patient’s insurance plan and the regulations laid out by the payer.|A service or an API to ensure that the procedures coded are billable (valid).|3|20|
|Generate a claim based on superbill|As a biller, I should be able to generate a claim based on superbill, patient info, rates and provider.|The medical biller takes the superbill from the medical coder and sends to billing module to generate claims. While claims may vary in format, they typically have the same basic information. Each claim contains the patient information (their demographic info and medical history) and the procedures performed (in CPT or HCPCS codes). Each of these procedures is paired with a diagnosis code (an ICD code) that demonstrates the medical necessity. The price for these procedures is listed as well.|Generate a claim automatically or manually.|3|20|
|Ensure the standards of compliance|As a biller, I should be able to ensure the standards of compliance, both for coding and format.|Once the biller has created the medical claim, he or she is responsible for ensuring that the claim meets the standards of compliance, both for coding and format. Billers must also ensure that the bill meets the standards of billing compliance. Billers typically must follow guidelines laid out by the Health Insurance Portability and Accountability Act (HIPAA) and the Office of the Inspector General (OIG).|A UI or service to perform compliance.|3|20|
|Send claims to clearinghouse and/or insurer|As a biller, I should be able to send claims to clearinghouse and/or insurer.|Send the amount they expect the payer to pay, as laid out in the payer’s contract with the patient and the provider.|A service or an API to send claims to insurer or Clearinghouse.|3|20|
|Receive claims back from clearinghouse and/or insurer|As a biller, I should be able to receive claims back from clearinghouse and/or insurer.|Once a claim reaches a payer, it undergoes a process called adjudication. In adjudication, a payer evaluates a medical claim and decides whether the claim is valid/compliant and, if so, how much of the claim the payer will reimburse the provider for. It’s at this stage that a claim may be accepted, denied, or rejected.|A service to accept and process claims from insurer or Clearinghouse.|3|20|
|Send left over amount to second or more insurer|As a biller, I should be able to send left over amount to second or more insurer.|If the patient has secondary insurance, the biller takes the amount left over after the primary insurance returns the approved claim and sends it to the patient’s secondary insurance|The service or an API to adjust the claim amount from 1st insurer and send to 2nd insurer.|3|20|
|Correct rejected bill and resend it|As a biller, I should be able to correct rejected bill and resend it.|A rejected claim is one that the payer has found some error with. If a claim is missing important patient information, or if there is a miscoded procedure or diagnosis, the claim will be rejected, and will be returned to the provider/biller. In the case of rejected claims, the biller may correct the claim and resubmit it.|A service or an API to process reject bills.|3|20|
|Process denied claim|As a biller, I should be able to process denied claim.|A denied claim is one that the payer refuses to process payment for the medical services rendered. This may occur when a provider bills for a procedure that is not included in a patient’s insurance coverage. This might include a procedure for a pre-existing condition (if the insurance plan does not cover such a procedure).|A service or an API to raise alert notification etc. on receiving denied claim.|3|20|
|Send an appeal in case of a discrepancies|As a biller, I should be able to to send an appeal in case of a discrepancies.|If there are any discrepancies, the biller/provider will enter into an appeal process with the payer. This process is complicated and depends on rules that are specific to payers and to the states in which a provider is located. Effectively, a claims appeal is the process by which a provider attempts to secure the proper reimbursement for their services. This can be a long and arduous process, which is why it’s imperative that billers create accurate, “clean” claims on the first go.|Create an service or an API to send an appeal to the insurer or Clearinghouse.|3|20|
|Make statement to the patient|As a biller, I should be able to make statement to the patient.|Once the biller has received the report from the payer, it’s time to make the statement for the patient. The statement is the bill for the procedure or procedures the patient received from the provider. Once the payer has agreed to pay the provider for a portion of the services on the claim, the remaining amount is passed to the patient.|Send email, push notification to the app and generate statement to the patient|3|20|
|Follow-up, send reminder to the patient|As a biller, I should be able to follow-up, send reminder to the patient.|Each provider has it’s own set of guidelines and timelines when it comes to bill payment, notifications, and collections, so based on the guidelines send out the reminder etc.|Based on the guidelines and duration send out reminder mail, notification.|3|20|

**Team-work Estimation**

|Team Members         |Estimation (Hrs per week) |
|---------------------|--------------------------|
|Rohit Sonawane       |15                        |
|Ankit Darekar        |15                        |
|Nitesh Lalan Jha     |15                        |
|Priyam A Sahoo       |15                        |
|Chandresh Sikarwar   |15                        |


**[Please find the APIs](https://chandreshsi.github.io/HealthCare---Billing-and-Revenue-management/)**

