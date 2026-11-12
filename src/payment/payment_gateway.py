from abc import ABC, abstractmethod
from decimal import Decimal

class PaymentGateway(ABC):
    @abstractmethod
    def process_payment(self,value : Decimal) -> bool:
        ...