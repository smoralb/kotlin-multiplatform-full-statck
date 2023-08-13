import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import react.FC
import react.Props
import react.dom.html.ReactHTML.h1
import react.dom.html.ReactHTML.li
import react.dom.html.ReactHTML.ul
import react.useEffectOnce
import react.useState

private val scope = MainScope()

/*
 Props are the properties used for the components in React, is like a class that has endless properties that you can pass it
 to the component.

// Functional component (FC) that receives a message and say how to render it.

function Greeting(props) {
  return <p>{props.message}</p>;
}

// Main component that renders the Greeting component with the specified message

function App() {
  return <Greeting message="Hello World!" />;
}

export default App;

 */
val App = FC<Props> {
    var shoppingList by useState(emptyList<ShoppingListItem>())

    // useEffectOnce is like init in VM, is launched only once when the component is rendered.
    useEffectOnce {
        scope.launch {
            shoppingList = getShoppingList()
        }
    }

    h1 {
        +"Full-Stack Shopping List"
    }
    ul {
        shoppingList.sortedByDescending(ShoppingListItem::priority).forEach { item ->
            li {
                key = item.toString()
                +"[${item.priority}] ${item.desc} "
                onClick = {
                    scope.launch {
                        deleteShoppingListItem(item)
                        shoppingList = getShoppingList()
                    }
                }
            }
        }
    }

    inputComponent {
        onSubmit = { input ->
            val cartItem = ShoppingListItem(
                desc = input.replace("!", ""),
                priority = input.count { it == '!' })
            scope.launch {
                addShoppingListItem(shoppingListItem = cartItem)
                shoppingList = getShoppingList()
            }
        }
    }
}