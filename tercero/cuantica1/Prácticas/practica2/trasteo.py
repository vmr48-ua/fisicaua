from qiskit import IBMQ

IBMQ.load_account() # Load account from disk
IBMQ.providers()    # List all available providers
IBMQ.get_provider(hub='ibm-q')