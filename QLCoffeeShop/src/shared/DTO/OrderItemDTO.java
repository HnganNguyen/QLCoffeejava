package shared.DTO;

public class OrderItemDTO {

    private ProductDTO product;
    private int quantity;

    public OrderItemDTO(ProductDTO product) {
        this.product = product;
        this.quantity = 1;
    }

    public ProductDTO getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void increase() {
        quantity++;
    }

    public void decrease() {
        if (quantity > 1) quantity--;
    }
}
