package com.code4galaxy.e_commerceapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
 interface CartDao {

  @Query("SELECT * FROM cart WHERE userId = :userId")
  suspend fun getCartItems(userId: String): List<CartEntity>


 @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertCartItem(cartItem: CartEntity)

  @Query("""
        UPDATE cart
        SET quantity = :quantity
        WHERE userId = :userId
        AND productId = :productId
  """)
  suspend fun updateQuantity(userId: String, productId: String, quantity: Int)



 @Query(
  """
        DELETE FROM cart
        WHERE userId = :userId
        AND productId = :productId
        """
 )
 suspend fun deleteCartItem(userId: String, productId: String)


 @Query(
  """
    SELECT * FROM cart
    WHERE userId = :userId
    AND productId = :productId
    LIMIT 1
    """
 )
 suspend fun getCartItem(
  userId: String,
  productId: String
 ): CartEntity?


  @Query("DELETE FROM cart WHERE userId = :userId")
  suspend fun clearCart(userId: String)
 }