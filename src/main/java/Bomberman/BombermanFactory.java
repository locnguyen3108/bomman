package Bomberman;

import Bomberman.Components.BombComponent;
import Bomberman.Components.Enemy.BALLOM;
import Bomberman.Components.Enemy.ONIL;
import Bomberman.Components.FlameComponent;
import Bomberman.Components.PlayerComponent;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.entity.components.IrremovableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyDef;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.physics.box2d.dynamics.FixtureDef;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static Bomberman.Contants.Contant.*;
import static com.almasb.fxgl.dsl.FXGLForKtKt.entityBuilder;

public class BombermanFactory implements EntityFactory {
    @Spawns("wall")
    public Entity newWall(SpawnData data) {
        return entityBuilder(data)
                .type(GameEntity.WALL)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new PhysicsComponent())
                .with(new CollidableComponent(true))
                .build();
    }

    @Spawns("brick")
    public Entity newBrick(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(GameEntity.BRICK)
                .view("brick.png")
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new PhysicsComponent())
                .with(new CollidableComponent(true))
                .build();
    }
    @Spawns("background")
    public Entity newBackground(SpawnData data) {
        return FXGL.entityBuilder()
                .view(new Rectangle(GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT, Color.rgb(0, 125, 0)))
                .zIndex(-100)
                .with(new IrremovableComponent())
                .build();
    }

    @Spawns("brick_break")
    public Entity newBrickBreak(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(GameEntity.BRICK_BREAK)
                .with(new Bomberman.Components.BrickBreakComponent())
                .viewWithBBox(new Rectangle(TILED_SIZE, TILED_SIZE, Color.TRANSPARENT))
                .atAnchored(new Point2D(0, 0), new Point2D(data.getX(), data.getY()))
                .zIndex(1)
                .build();
    }

    @Spawns("door")
    public Entity newDoor(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(GameEntity.DOOR)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new CollidableComponent(true))
                .build();
    }

    @Spawns("player")
    public Entity newPlayer(SpawnData data) {
        PhysicsComponent physics = new PhysicsComponent();
        physics.setFixtureDef(new FixtureDef().friction(0).density(0.1f));
        BodyDef bd = new BodyDef();
        bd.setFixedRotation(true);
        bd.setType(BodyType.DYNAMIC);
        physics.setBodyDef(bd);

        return FXGL.entityBuilder(data)
                .type(GameEntity.PLAYER)
                .bbox(new HitBox(new Point2D(2, 2), BoundingShape.circle(20)))
                .with(physics)
                .with(new PlayerComponent())
                .with(new CollidableComponent(true))
                .zIndex(5)
                .build();
    }

    @Spawns("enemy1")
    public Entity newEnemy1(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(GameEntity.BAlLOM)
                .bbox(new HitBox(new Point2D(5, 5), BoundingShape.box(38, 38)))
                .with(new BALLOM())
                .with(new CollidableComponent(true))
                .build();
    }

    @Spawns("enemy2")
    public Entity newEnemy2(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(GameEntity.ONIL)
                .bbox(new HitBox(new Point2D(5, 5), BoundingShape.box(38, 38)))
                .with(new ONIL())
                .with(new CollidableComponent(true))
                .build();
    }

    @Spawns("bomb")
    public Entity newBomb(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(GameEntity.BOMB)
                .atAnchored(new Point2D(0, 0), new Point2D(data.getX(), data.getY()))
                .bbox(new HitBox(new Point2D(2, 2), BoundingShape.circle(22)))
                .with(new BombComponent())
                .with(new CollidableComponent(true))
                .zIndex(-1)
                .build();
    }

    @Spawns("virtual_bomb")
    public Entity newVirtualBomb(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(GameEntity.VIRTUAL_BOMB)
                .bbox(new HitBox(new Point2D(0, 0), BoundingShape.box(TILED_SIZE, TILED_SIZE)))
                .atAnchored(new Point2D(0, 0), new Point2D(data.getX(), data.getY()))
                .with(new PhysicsComponent())
                .build();
    }

    @Spawns("central_flame")
    public Entity newCFlame(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(GameEntity.FLAME)
                .bbox(new HitBox(new Point2D(2, 2), BoundingShape.box(TILED_SIZE - 4, TILED_SIZE - 4)))
                .with(new FlameComponent("central_flame.png"))
                .atAnchored(new Point2D(0, 0), new Point2D(data.getX(), data.getY()))
                .with(new CollidableComponent(true))
                .zIndex(-1)
                .build();
    }

    @Spawns("top_down_flame")
    public Entity newTDFlame(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(GameEntity.FLAME)
                .bbox(new HitBox(new Point2D(2, TILED_SIZE - data.getZ() - 3), BoundingShape.box(TILED_SIZE - 4, data.getZ())))
                .with(new FlameComponent("top_down_flame.png"))
                .atAnchored(new Point2D(0, 0), new Point2D(data.getX(), data.getY()))
                .with(new CollidableComponent(true))
                .zIndex(-1)
                .build();
    }

    @Spawns("top_up_flame")
    public Entity newTUFlame(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(GameEntity.FLAME)
                .bbox(new HitBox(new Point2D(2, 3), BoundingShape.box(TILED_SIZE - 4, data.getZ())))
                .with(new FlameComponent("top_up_flame.png"))
                .atAnchored(new Point2D(0, 0), new Point2D(data.getX(), data.getY()))
                .with(new CollidableComponent(true))
                .zIndex(-1)
                .build();
    }

    @Spawns("top_right_flame")
    public Entity newTRFlame(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(GameEntity.FLAME)
                .bbox(new HitBox(new Point2D(TILED_SIZE - data.getZ() - 3, 2), BoundingShape.box(data.getZ(), TILED_SIZE - 4)))
                .with(new FlameComponent("top_right_flame.png"))
                .atAnchored(new Point2D(0, 0), new Point2D(data.getX(), data.getY()))
                .with(new CollidableComponent(true))
                .zIndex(-1)
                .build();
    }

    @Spawns("top_left_flame")
    public Entity newTLFlame(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(GameEntity.FLAME)
                .bbox(new HitBox(new Point2D(3, 2), BoundingShape.box(data.getZ(), TILED_SIZE - 4)))
                .with(new FlameComponent("top_left_flame.png"))
                .atAnchored(new Point2D(0, 0), new Point2D(data.getX(), data.getY()))
                .with(new CollidableComponent(true))
                .zIndex(-1)
                .build();
    }

    @Spawns("up_flame")
    public Entity newUFlame(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(GameEntity.FLAME)
                .bbox(new HitBox(new Point2D(2, 3), BoundingShape.box(TILED_SIZE - 4, data.getZ())))
                .with(new FlameComponent("up_flame.png"))
                .atAnchored(new Point2D(0, 0), new Point2D(data.getX(), data.getY()))
                .with(new CollidableComponent(true))
                .zIndex(-1)
                .build();
    }

    @Spawns("down_flame")
    public Entity newDFlame(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(GameEntity.FLAME)
                .bbox(new HitBox(new Point2D(2, TILED_SIZE - data.getZ() - 3), BoundingShape.box(TILED_SIZE - 4, data.getZ())))
                .with(new FlameComponent("down_flame.png"))
                .atAnchored(new Point2D(0, 0), new Point2D(data.getX(), data.getY()))
                .with(new CollidableComponent(true))
                .zIndex(-1)
                .build();
    }

    @Spawns("left_flame")
    public Entity newLFlame(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(GameEntity.FLAME)
                .bbox(new HitBox(new Point2D(3, 2), BoundingShape.box(data.getZ(), TILED_SIZE - 4)))
                .with(new FlameComponent("left_flame.png"))
                .atAnchored(new Point2D(0, 0), new Point2D(data.getX(), data.getY()))
                .with(new CollidableComponent(true))
                .zIndex(-1)
                .build();
    }

    @Spawns("right_flame")
    public Entity newRFlame(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(GameEntity.FLAME)
                .bbox(new HitBox(new Point2D(TILED_SIZE - data.getZ() - 3, 2), BoundingShape.box(data.getZ(), TILED_SIZE - 4)))
                .with(new FlameComponent("right_flame.png"))
                .atAnchored(new Point2D(0, 0), new Point2D(data.getX(), data.getY()))
                .with(new CollidableComponent(true))
                .zIndex(-1)
                .build();
    }


    @Spawns("powerup_flames")
    public Entity newItem(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(GameEntity.POWERUP_FLAMES)
                .view("powerup_flames.png")
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new PhysicsComponent())
                .with(new CollidableComponent(true))
                .zIndex(-1)
                .build();
    }

    @Spawns("powerup_bombs")
    public Entity newItem2(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(GameEntity.POWERUP_BOMBS)
                .view("powerup_bombs.png")
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new PhysicsComponent())
                .with(new CollidableComponent(true))
                .zIndex(-1)
                .build();
    }

    @Spawns("powerup_speed")
    public Entity newItem3(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(GameEntity.POWERUP_SPEED)
                .view("powerup_speed.png")
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new PhysicsComponent())
                .with(new CollidableComponent(true))
                .zIndex(-1)
                .build();
    }

    @Spawns("powerup_flamepass")
    public Entity newItem4(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(GameEntity.POWERUP_FLAMEPASS)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .view("powerup_flamepass.png")
                .with(new PhysicsComponent())
                .with(new CollidableComponent(true))
                .zIndex(-1)
                .build();
    }

    @Spawns("powerup_life")
    public Entity newItem5(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(GameEntity.POWERUP_LIFE)
                .view("powerup_life.png")
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new PhysicsComponent())
                .with(new CollidableComponent(true))
                .zIndex(-1)
                .build();
    }
}
